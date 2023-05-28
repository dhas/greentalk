package furhatos.app.furgui.flow.chatbot

import com.theokanning.openai.OpenAiService
import com.theokanning.openai.completion.CompletionRequest
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.edit.EditRequest;
import furhatos.flow.kotlin.DialogHistory
import furhatos.flow.kotlin.Furhat

/** API Key to GPT3 language model. Get access to the API and genereate your key from: https://openai.com/api/ **/
val serviceKey = ""

class OpenAIChatbot() {

    val service = OpenAiService(serviceKey)

    // Read more about these settings: https://beta.openai.com/docs/introduction
    var temperature = 0.9 // Higher values means the model will take more risks. Try 0.9 for more creative applications, and 0 (argmax sampling) for ones with a well-defined answer.
    var maxTokens = 50 // Length of output generated. 1 token is on average ~4 characters or 0.75 words for English text
    var topP = 1.0 // 1.0 is default. An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.
    var frequencyPenalty = 0.0 // Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing frequency in the text so far, decreasing the model's likelihood to repeat the same line verbatim.
    var presencePenalty = 0.6 // Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear in the text so far, increasing the model's likelihood to talk about new topics.

    val agentName = "Garden designer"

    val description = "The conversation is between a $agentName and a Human. " +
            "The $agentName is friendly and helpful."

    fun getNextResponse(): String {
        /** The prompt for the chatbot includes a context of ten "lines" of dialogue. **/

        val history = Furhat.dialogHistory.all.takeLast(10).mapNotNull {
            when (it) {
                is DialogHistory.ResponseItem -> {
                    "Human: ${it.response.text}"
                }
                is DialogHistory.UtteranceItem -> {
                    "$agentName: ${it.toText()}"
                }
                else -> null
            }
        }.joinToString(separator = "\n")
        val prompt = "$description\n\n$history\n$agentName:"
        println("-----")
        println(prompt)
        println("-----")
        val completionRequest = CompletionRequest.builder()
            .temperature(temperature)
            .maxTokens(maxTokens)
            .topP(topP)
            .frequencyPenalty(frequencyPenalty)
            .presencePenalty(presencePenalty)
            .prompt(prompt)
            .stop(listOf("\n", " Human:", " $agentName:"))
            .echo(true)
            .build();

        try {
            val completion = service.createCompletion("davinci", completionRequest).getChoices().first().text
            val response = completion.drop(prompt.length).trim()
            return response
        } catch (e: Exception) {
            println(e)
            println("Problem with connection to OpenAI")
        }
        return "I am not sure what to say"

    }

    fun summarizeResponse(): String {
        val context = "Generate keywords for the conversation"

        val history = Furhat.dialogHistory.all.mapNotNull {
            when (it) {
                is DialogHistory.ResponseItem -> {
                    "Human: ${it.response.text}"
                }
                is DialogHistory.UtteranceItem -> {
                    "$agentName: ${it.toText()}"
                }
                else -> null
            }
        }.joinToString(separator = ".")

        val prompt = context + history
        println("--Summary--")
        println(prompt)
        println("--Summary--")

        val completionRequest = CompletionRequest.builder()
                .temperature(0.9)
                .maxTokens(50)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.6)
                .prompt(prompt)
                .stop(listOf("$agentName:", "Human:"))
                .echo(false)
                .model("text-davinci-003")
                .build();

        try {
        val completion = service.createCompletion(completionRequest)
                .getChoices().first().text
        val response = completion.trim()
        return response
        } catch (e: Exception) {
        println(e)
        println("Problem with connection to OpenAI")
        }
        return "I am not sure what to say"
    }


    fun getImage(imagePrompt: String ): String {
        val imageRequest = CreateImageRequest.builder()
                .prompt(imagePrompt)
                .n(1)
                .size("512x512")
                .build()
        println(imageRequest)
        try {
            val image = service.createImage(imageRequest).getData().first().url
            val imgRes = image
            println("-----")
            println(imgRes)
            return imgRes
        } catch (e: Exception) {
            println(e)
            println("Problem with connection to OpenAI Image")
        }
        return "I am not sure what to say"
    }
}
