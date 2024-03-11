const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const ChatSchema = new Schema({
  userId: {
    type: Schema.Types.ObjectId,
  },
  created_at: {
    type: Date,
    default: Date.now,
    required: false
  },
  content: {
    type: String,
    required: true
  },
  idConversation: {
    type: Schema.Types.ObjectId
  }
});

module.exports = mongoose.model("Chat", ChatSchema);