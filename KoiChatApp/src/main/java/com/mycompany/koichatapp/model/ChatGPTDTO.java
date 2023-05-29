/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.koichatapp.model;

import java.util.ArrayList;

/**
 *
 * @author datcy
 */
public class ChatGPTDTO {
    public String id;
    public long created;
    public String model;
    public String object;
    public ArrayList<Choice> choices;
    public Usage usage;

    public class Choice {
        public String finish_reason;
        public int index;
        public Message message;
        public String text;
    }

    public class Message {
        public String content;
        public String role;
    }

    public class Usage {
        public int prompt_tokens;
        public int completion_tokens;
        public int total_tokens;
    }
}
