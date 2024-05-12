import React, {
  useEffect,
  useState,
  useCallback,
  useRef,
  buildMessage,
} from "react";
import { Calendar, momentLocalizer, Slot } from "react-big-calendar";
import "react-big-calendar/lib/css/react-big-calendar.css";
import moment from "moment";
import "../css/Calendar.css";
import axios from "axios";

import { address, flask_port } from "./Endpoint";
import FetchEnpoint from "./EndpointFinder";

var endpoint1 = await FetchEnpoint() + "/tasks";
var endpoint2 = await FetchEnpoint() + "/teamview";

const localizer = momentLocalizer(moment);

const messages = {
  previous: "←",
  next: "→",
};

interface Props {
  width: integer;
  height: integer;
  handleSelectSlot: () => void;
  slotPropGetter: () => void;
  handleSelectEvent: () => void;
}

const WeeklyCalendar = ({
  width,
  height,
  handleSelectSlot,
  slotPropGetter,
  handleSelectEvent,
}: Props) => {
  const startingHour = 8;
  const timeGutterFormat = "H:mm";
  const [events, setEvents] = useState([]);
  const localUser = localStorage.getItem("LoggedUser");
  const localTeam = 0; //da gestire quando si implementano i team
  const endpoint_event = FetchEnpoint() + "/getColor";
  const currentPath = window.location.href; //get the current position
  const [colors, setColors] = useState({});

  useEffect(() => {
    const eventIds = events.map((event) => event.id).join(",");
    axios
      .get(endpoint_event, {
        params: {
          event: eventIds,
        },
      })
      .then((response) => {
        setColors(response.data);
      })
      .catch((error) => {
        console.error("Error fetching color:", error);
      });
  }, [events]);

  const eventStyleGetter = (event, start, end, isSelected) => {
    if (event.type === "personal") {
      const style = {
        backgroundColor: "#07fcaf",
        borderRadius: "10px",
        color: "black",
        border: "0px",
        display: "inline-block",
      };
      return {
        style: style,
      };
    } else {
      const style = {
        backgroundColor: colors[event.id],
        borderRadius: "10px",
        color: "black",
        border: "0px",
        display: "inline-block",
      };
      return {
        style: style,
      };
    }
  };
  function filterEventsWithUniqueIds(events) {
    const eventIds = new Set();
    return events.filter((event) => {
      if (eventIds.has(event.id)) {
        // This event has the same id as a previously seen event, so filter it out
        return false;
      }
      // This event has a unique id, so add it to the set and keep it
      eventIds.add(event.id);
      return true;
    });
  }

  const MyEvent = ({ event }) => <div>{event.title}</div>;

  const components = {
    agenda: {
      event: MyEvent, // Use your custom event component
    },
    event: MyEvent,
  };

  useEffect(() => {
    if (currentPath === "/home") {
      axios
        .get(endpoint1, {
          params: {
            user: localUser,
          },
        })
        .then((response) => {
          const tasks = response.data;

          const formattedEvents = tasks.map((task) => ({
            id: task.id,
            title: task.title,
            start: new Date(task.start),
            end: new Date(task.end),
            description: task.description,
            status: task.status,
            type: task.type,
            duration: task.duration,
            member: task.member,
          }));

          // Clear existing events and set the new ones
          setEvents([]);
          setEvents(filterEventsWithUniqueIds(formattedEvents));
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    } else {
      // If I'm not on the home and so I'm on the team view, I want to visualize my tasks and all the events created in the group
      axios
        .get(endpoint2, {
          params: {
            user: localUser,
            team: localTeam, // To be handled when implementing teams
          },
        })
        .then((response) => {
          const tasks = response.data;

          const formattedEvents = tasks.map((task) => ({
            id: task.id,
            title: task.title,
            start: new Date(task.start),
            end: new Date(task.end),
            description: task.description,
            status: task.status,
            type: task.type,
            duration: task.duration,
            member: task.member,
          }));

          // Clear existing events and set the new ones

          setEvents([]);
          setEvents(filterEventsWithUniqueIds(formattedEvents));
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    }
  }, []);

  return (
    <div className="weekly-calendar-container">
      <Calendar
        localizer={localizer}
        views={["day", "week", "month"]}
        defaultView="week"
        startAccessor="start"
        endAccessor="end"
        min={new Date().setHours(startingHour, 0, 0, 0)}
        style={{ height: height, width: width }}
        events={events}
        formats={{
          timeGutterFormat,
        }}
        components={components}
        messages={messages}
        onSelectEvent={handleSelectEvent}
        eventPropGetter={eventStyleGetter}
        onSelectSlot={handleSelectSlot}
        selectable
        slotPropGetter={slotPropGetter}
      />
    </div>
  );
};

export default WeeklyCalendar;
