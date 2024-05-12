import { address, flask_port, flask_port_2 } from "./Endpoint";
import axios from "axios";

export default async function FetchEnpoint() {
  //Lista di tutti gli endpoint possibili
  var EndpointList = [address + flask_port, address + flask_port_2];
  // shuffle(EndpointList);

  let endpoint = "";

  for (let i = 0; i < EndpointList.length; i++) {
    endpoint = EndpointList[i];
    try {
      await fetch(endpoint);
    } catch (error) {
      continue;
    }
    return endpoint;
  }

  return "";
}

function shuffle(array) {
  let currentIndex = array.length,
    randomIndex;

  // While there remain elements to shuffle.
  while (currentIndex > 0) {
    // Pick a remaining element.
    randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex--;

    // And swap it with the current element.
    [array[currentIndex], array[randomIndex]] = [
      array[randomIndex],
      array[currentIndex],
    ];
  }

  return array;
}
