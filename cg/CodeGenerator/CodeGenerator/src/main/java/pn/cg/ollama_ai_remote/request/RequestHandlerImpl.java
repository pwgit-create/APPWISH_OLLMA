package pn.cg.ollama_ai_remote.request;


import pn.cg.datastorage.CodeGeneratorConfig;
import pn.cg.datastorage.constant.QuestionConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.amithkoujalgi.ollama4j.core.OllamaAPI;
import io.github.amithkoujalgi.ollama4j.core.exceptions.OllamaBaseException;
import io.github.amithkoujalgi.ollama4j.core.models.OllamaResult;
import io.github.amithkoujalgi.ollama4j.core.utils.Options;
import io.github.amithkoujalgi.ollama4j.core.utils.OptionsBuilder;
import io.github.amithkoujalgi.ollama4j.core.utils.PromptBuilder;

import java.io.IOException;


public class RequestHandlerImpl implements RequestHandler {

    private final String HOST="http://localhost:11434/";
    

    private static final Logger log = LoggerFactory.getLogger(RequestHandlerImpl.class);

    public RequestHandlerImpl() {

    }


    @Override
    public  String sendQuestionToOllamaInstance(String question) {
        
        OllamaAPI api = new OllamaAPI(HOST);
 

        Options options =
                new OptionsBuilder()
                        .setNumCtx(8192)  //16384
                        .setNumGqa(8)
                        .setNumGpu(50)
                        .setTemperature(0.9f)
                        .setNumPredict(-1) // -1
                        .setTopK(20)
                        .build();

                        PromptBuilder promptBuilder = new PromptBuilder()
                        .addLine(QuestionConstants.GREETING_TO_MODEL)
                        .addLine(question)
                        .addLine(QuestionConstants.WITH_MAIN_METHOD)
                        .addLine(QuestionConstants.AND_CORRECT_IMPORTS)
                        .addLine(QuestionConstants.INCLUDE_IN_ONE_FILE)
                        .addLine(QuestionConstants.AND_MAKE_SURE_CORRECT_NUMBER_OF_BRACE_BRACKETS_ARE_USED_AT_THE_END_OF_THE_JAVA_CODE)
                        .addLine(QuestionConstants.MARK_START_CHAR_DELIMITER)
                        .addLine(QuestionConstants.MARK_THE_END_CHAR_DELIMITER)
                        .addLine(QuestionConstants.MAKE_SURE_THAT_START_DELIMITER_CHAR_IS_USED_ONCE)
                        .addLine(QuestionConstants.MAKE_SURE_THAT_END_DELIMITER_CHAR_IS_USED_ONCE)
                        .addLine(QuestionConstants.NO_JAVA_FX)
                        .addLine(QuestionConstants.NO_SPECIAL_LIBRARIES)
                        .addLine(QuestionConstants.MAKE_SURE_IT_WORKS_ON_JAVA_19)
                        .addLine(QuestionConstants.IMPLEMENT_AS_MUCH_AS_POSSIBLE);
    api.setRequestTimeoutSeconds(100000);
       log.info("Is OLLAMA server alive? -> "+ api.ping()); // This is your local OLLAMA server running on localhost and is used for the LLM model
        CodeGeneratorConfig codeGeneratorConfig = new CodeGeneratorConfig();
        OllamaResult result = null;
        try {
            result = api.ask(codeGeneratorConfig.GetOllamaModelName(), promptBuilder.build(),options);
            
        } catch (OllamaBaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String outputFromOllamaAPI = (result.getResponse());
        log.debug(outputFromOllamaAPI);
        return outputFromOllamaAPI;
    }
}




