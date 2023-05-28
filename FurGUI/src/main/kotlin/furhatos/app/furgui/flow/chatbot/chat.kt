package furhatos.app.furgui.flow.chatbot

import furhatos.app.furgui.SendImage
import furhatos.app.furgui.flow.*
import furhatos.app.furgui.setting.activate
import furhatos.app.furgui.setting.hostPersona
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val MainChat = state(Parent) {
    onEntry {
        Furhat.dialogHistory.clear()
        furhat.ask("What would you like to see in your garden?")
        reentry()
    }

    onReentry {
        furhat.listen()
    }

    onResponse("generate image", "visualize") {
        furhat.say("Sure, please wait")
        val response = call { currentPersona.chatbot.summarizeResponse() } as String
        val imageRes = call { currentPersona.chatbot.getImage(response) } as String
        send(SendImage(imageRes))
    }

    onResponse("can we stop", "goodbye") {
        furhat.say("Okay, goodbye")
        delay(2000)
        furhat.say {
            random {
                +"I hope that was fun"
                +"I hope you enjoyed that"
                +"I hope you found that interesting"
            }
        }
        goto(AfterChat)
    }

    onResponse {
        furhat.gesture(GazeAversion(2.0))
        val response = call {
            currentPersona.chatbot.getNextResponse()
        } as String
        furhat.say(response)
        reentry()
    }

    onNoResponse {
        reentry()
    }
}

val AfterChat: State = state(Parent) {

    onEntry {
        furhat.ask("Would you like to try out another garden?")
    }

    onPartialResponse<Yes> {
        raise(it.secondaryIntent)
    }

    onResponse<Yes> {
        goto(MainChat)
    }

    onResponse<No> {
        furhat.say("Okay, goodbye then")
        goto(Idle)
    }

}