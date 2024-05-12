import React, { useState, useEffect, forwardRef, useImperativeHandle } from 'react';
import io from 'socket.io-client';

import alarm from "../icons/alarm.png";
import account from "../icons/user.png";
import chat_alarm from "../icons/chat_alarm.png";
import FetchEnpoint from './EndpointFinder';

import { address, flask_port } from './Endpoint';

const endpoint1 = "http://localhost:5000";
const endpoint2 = "http://localhost:5001";

const WebSocketComponent =  forwardRef((props, ref) => {
  const username = localStorage.getItem('LoggedUser');
  let newSocket = null;

  const sendMessage = () => {
    console.log('CHIAMATA');
  };

  useImperativeHandle(ref, () => ({
    sendMessage:sendMessage,
  }));

  useEffect(() => {
    // New WebSocket connection at start
    newSocket = io(endpoint1, {reconnection:false, timeout:5000});

    newSocket.on('connect', () => {
      console.log('[INFO] Connected to the WebSocket server.');

      // Send to the server the username of the logged user
      newSocket.emit('initial_data', username);
    });

    // Receive messages from the server
    newSocket.on('message', (message) => {
      console.log('[INFO] Received message: '+message);
    });


    // Notifications from the server
    newSocket.on('invite_notification', (message) => {
      console.log('[INFO] Received notification: '+message);
      var bellIcon = document.getElementById('bell');
      bellIcon.src = alarm;
    });

    newSocket.on('message_notification', (message) => {
      console.log('[INFO] Received notification: '+message);
      var bellIcon = document.getElementById('bell');
      bellIcon.src = alarm;
    });

    newSocket.on('event_notification', (message) => {
      console.log('[INFO] Recieved notification: '+message);
      var bellIcon = document.getElementById('bell');
      bellIcon.src = alarm;
    });

    newSocket.on('chat_message', (message) => {
      console.log('[INFO] Recieved chat message: '+message[1]);

      if (document.getElementById('bubbleChat')) {
        var chatIcon = document.getElementById('bubbleChat');
        chatIcon.src = chat_alarm;
      }

      // Creation of the chat message entry
      if(document.getElementById('ChatBody')) {
        const container = document.getElementById("ChatBody");
        const div = document.createElement("div");
        div.classList.add("ChatEntry");
        container.appendChild(div);
      
        const img = document.createElement("img");
        img.src = account;
        div.appendChild(img);
      
        const div2 = document.createElement("div");
        div2.classList.add("ChatEntryText");
      
        const h2 = document.createElement("h2");
        h2.innerText = message[0];
        div2.appendChild(h2);
      
        const h3 = document.createElement("h3");
        h3.innerText = message[1];
        div2.appendChild(h3);
      
        const h5 = document.createElement("h5");
        h5.innerText = message[2]+" - "+message[3];
        div2.appendChild(h5);
      
        div.appendChild(div2);

        const scrollChat = document.getElementById('ChatBody');
        scrollChat.scrollTop = scrollChat.scrollHeight;
      }
    });
      
    newSocket.on('survey_notification', (message) => {
      console.log('[INFO] Recieved notification: '+message);
      var bellIcon = document.getElementById('bell');
      bellIcon.src = alarm;
    });


    // New socket if the primary fails
    newSocket.on('disconnect', () => {
      newSocket = io(endpoint2,{reconnection:false, timeout:5000});

      newSocket.on('connect', () => {
        console.log('[INFO] Connected to the WebSocket server.');
  
        // Send to the server the username of the logged user
        newSocket.emit('initial_data', username);
      });
  
      // Receive messages from the server
      newSocket.on('message', (message) => {
        console.log('[INFO] Received message: '+message);
      });
  
  
      // Notifications from the server
      newSocket.on('invite_notification', (message) => {
        console.log('[INFO] Received notification: '+message);
        var bellIcon = document.getElementById('bell');
        bellIcon.src = alarm;
      });
  
      newSocket.on('message_notification', (message) => {
        console.log('[INFO] Received notification: '+message);
        var bellIcon = document.getElementById('bell');
        bellIcon.src = alarm;
      });
  
      newSocket.on('event_notification', (message) => {
        console.log('[INFO] Recieved notification: '+message);
        var bellIcon = document.getElementById('bell');
        bellIcon.src = alarm;
      });
  
      newSocket.on('chat_message', (message) => {
        console.log('[INFO] Recieved chat message: '+message[1]);
  
        if (document.getElementById('bubbleChat')) {
          var chatIcon = document.getElementById('bubbleChat');
          chatIcon.src = chat_alarm;
        }
  
        // Creation of the chat message entry
        if(document.getElementById('ChatBody')) {
          const container = document.getElementById("ChatBody");
          const div = document.createElement("div");
          div.classList.add("ChatEntry");
          container.appendChild(div);
        
          const img = document.createElement("img");
          img.src = account;
          div.appendChild(img);
        
          const div2 = document.createElement("div");
          div2.classList.add("ChatEntryText");
        
          const h2 = document.createElement("h2");
          h2.innerText = message[0];
          div2.appendChild(h2);
        
          const h3 = document.createElement("h3");
          h3.innerText = message[1];
          div2.appendChild(h3);
        
          const h5 = document.createElement("h5");
          h5.innerText = message[2]+" - "+message[3];
          div2.appendChild(h5);
        
          div.appendChild(div2);
  
          const scrollChat = document.getElementById('ChatBody');
          scrollChat.scrollTop = scrollChat.scrollHeight;
        }
      });
        
      newSocket.on('survey_notification', (message) => {
        console.log('[INFO] Recieved notification: '+message);
        var bellIcon = document.getElementById('bell');
        bellIcon.src = alarm;
      });
    });
    


    // Chiudi la connessione WebSocket quando il componente viene smontato
    return () => {
      if (newSocket && !username) {
        newSocket.disconnect();
      }
    };
  }, []);

  return (
    <div></div>
  );

});

export {WebSocketComponent};