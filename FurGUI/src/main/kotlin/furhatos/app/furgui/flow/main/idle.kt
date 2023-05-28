package furhatos.app.furgui.flow

import furhatos.app.furgui.setting.activate
import furhatos.app.furgui.setting.hostPersona
import furhatos.nlu.common.*
import furhatos.flow.kotlin.*
import furhatos.records.Location

val Idle : State = state {
    onEntry {
        activate(hostPersona)
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(Greeting)
    }

}





