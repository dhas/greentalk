package furhatos.app.furgui.flow

import furhatos.app.furgui.flow.chatbot.MainChat
import furhatos.event.senses.SenseSkillGUIConnected
import furhatos.app.furgui.flow.chatbot.serviceKey
import furhatos.app.furgui.setting.activate
import furhatos.app.furgui.setting.distanceToEngage
import furhatos.app.furgui.setting.hostPersona
import furhatos.app.furgui.setting.maxNumberOfUsers
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.records.Location
import furhatos.skills.HostedGUI

// Our GUI declaration
val PORT = 1234
val GUI = HostedGUI("VisualizeGUI", "assets/greentalk/build", PORT)


// Starting state, before our GUI has connected.
val NoGUI: State = state(null) {
    onEvent<SenseSkillGUIConnected> {
        goto(Init)
    }
}

val Init: State = state(NoGUI) {
    init {

        /** Check API key for the OpenAI GPT3 language model has been set */
        if (serviceKey.isEmpty()) {
            println("Missing API key for OpenAI GPT3 language model. ")
            exit()
        }

        /** Set the Persona */
        activate(hostPersona)

        /** start the interaction */
       goto(InitFlow)
    }

}

val InitFlow: State = state() {
    onEntry {
        furhat.attend(users.userClosestToPosition(Location(0.0, 0.0, 0.5)))
        goto(Greeting)
    }
}
