package tn.esprit.crud.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatbotController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField userInput;

    @FXML
    private void sendMessage(ActionEvent event) {
        String userMessage = userInput.getText();
        appendMessage("User: " + userMessage);

        // Add your chatbot logic here
        String chatbotResponse = getChatbotResponse(userMessage);
        appendMessage("Chatbot: " + chatbotResponse);

        // Clear the user input field
        userInput.clear();
    }

    private void appendMessage(String message) {
        chatArea.appendText(message + "\n");
    }

    private String getChatbotResponse(String userMessage) {
        String lowerCaseMessage = userMessage.toLowerCase();

        switch (lowerCaseMessage) {
            case "reclamation":
                return "Our customer support team is here to help you with reclamations.";
            case "response":
                return "Responses to your queries will be provided within the specified time.";
            case "hello":
                return "Hello! How can I assist you today?";
            case "kamel":
                return "Hello! kamel";
            default:
                return "I'm sorry, I don't understand. Please ask about reclamations, responses, or anything else.";
        }
    }
}
