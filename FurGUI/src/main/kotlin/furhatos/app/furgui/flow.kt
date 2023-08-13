package furhatos.app.furgui

import furhatos.event.senses.SenseSkillGUIConnected
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice
import furhatos.records.Record
import furhatos.skills.HostedGUI
import furhatos.util.Gender
import furhatos.util.Language

// Our GUI declaration
val GUI = HostedGUI("ExampleGUI", "assets/greentalk/build", PORT)
val VARIABLE_SET = "VariableSet"
val CLICK_GRID = "ClickGrid"

// Starting state, before our GUI has connected.
val NoGUI: State = state(null) {
    onEvent<SenseSkillGUIConnected> {
        goto(GUIConnected)
    }
}

/*
    Here we know our GUI has connected. Since the user might close down the GUI and then reopen
    again, we inherit our handler from the NoGUI state. An edge case might be that a second GUI
    is opened, but this is not accounted for here.

 */
val GUIConnected = state(NoGUI) {
    onEntry {
        // Pass data to GUI
        send(DataDelivery(rows, cols, gardenObjects))
         // Directly respond with the value we get from the event, with a fallback
        furhat.setVoice(language=Language.ENGLISH_US, gender=Gender.MALE)
        furhat.say("Hello! Good to meet you, let's see if we can make you a nice garden plan.")
        furhat.say("There are a few questions I'd like to ask you before we start.")
        goto(StartingQuestions)
//        goto(ChoosingGrid)
    }



    // Users saved some input
    onEvent(VARIABLE_SET) {
        // Get data from event
        val data = it.get("data") as Record
        val variable = data.getString("variable")
        val value = data.getString("value")

        // Get answer depending on what variable we changed and what the new value is, and speak it out
        val answer = inputFieldData[variable]?.invoke(value)
        furhat.say(answer ?: "Something went wrong")

        // Let the GUI know we're done speaking, to unlock buttons
        send(SPEECH_DONE)
    }
}