package com.sda.cz5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Joke {

    public boolean error;
    public String category;
    public String type;
    public String joke;
    public Flags flags;
    public int id;
    public boolean safe;
    public String lang;

    public static class Flags {
        public boolean nsfw;
        public boolean racist;
        public boolean sexist;
        public boolean religious;
        public boolean political;
        public boolean explicit;
    }
}