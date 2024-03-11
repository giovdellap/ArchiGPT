const mongoose = require('mongoose');

//------------ Event Schema ------------//
const EventSchema = new mongoose.Schema({

  name: {
    type: String,
    required: true
  },
  descrizione: {
    type: String,
  },
  img:
  {
      data: Buffer,
      contentType: String
  },
  date:{
    type: Date
  },
  city:{
    type: String
  },
  locale:{
    type: String
  },
  manager:{
    type: String
  },
  categoria:{
    type: String
  },
  num_bigl:{
    type: Number
  },
  prezzo:{
    type: Number
  },
  tipo:{
    type: String
  },
  bigl_rimanenti:{
    type:Number
  }


}, { timestamps: true });

const Event = mongoose.model('Event', EventSchema);

module.exports = Event;