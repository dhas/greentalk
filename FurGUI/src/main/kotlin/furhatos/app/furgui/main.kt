package furhatos.app.furgui

import furhatos.app.furgui.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*
import furhatos.nlu.LogisticMultiIntentClassifier

class FurguiSkill : Skill() {
    override fun start() {
        Flow().run(NoGUI)
    }
}

fun main(args: Array<String>) {
    LogisticMultiIntentClassifier.setAsDefault()
    Skill.main(args)
}













