const mongoose = require("mongoose");
const { Schema } = mongoose;

const UserSchema = new Schema({
    isCustomer: {
        type: Boolean,
        required: true,
    },
    fullname: {
        type: String,
        required: true,
    },

    birth: {
        type: String,
        required: true,
    },
    fiscalcode: {
        type: String,
        required: true,
    },
    email: {
        type: String,
        required: true,
        unique: true,
    },
    password: {
        type: String,
        required: true,
    },
    activityName: String,
    fiscalAddress: String,
    city: String,
    vatNumber: String,
    ibanCode: String,
    ratingValue: {type: Number, default: 0},
    numberOfRatings: {type: Number, default: 0}
});

UserSchema.set('toJSON', {
    transform: (document, returnedObject) => {
        returnedObject.id = returnedObject._id.toString()
        delete returnedObject._id
        delete returnedObject.__v
        //do not reveal passwordHash
        delete returnedObject.password
    }
})


module.exports = mongoose.model("User", UserSchema);