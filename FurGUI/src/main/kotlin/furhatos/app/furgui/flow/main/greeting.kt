package furhatos.app.furgui.flow

import furhatos.app.furgui.flow.chatbot.MainChat
import furhatos.app.furgui.setting.Persona
import furhatos.app.furgui.setting.hostPersona
import furhatos.flow.kotlin.*
import furhatos.records.Location

val Greeting = state(Parent) {

    onEntry {
        furhat.attend(users.userClosestToPosition(Location(0.0, 0.0, 0.5)))
        furhat.say("Hi im your personal gardener, help me create your perfect garden")
        if (furhat.askYN("Shall we begin?") == true) {
            furhat.say("Good, let's try it out")
        } else {
                furhat.say("Okay, maybe another time then")
                goto(Idle)
        }
        goto(MainChat)
    }
}

var currentPersona: Persona = hostPersona