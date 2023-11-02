package com.ns.jokeservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Joke {
    private String joke;
    private Flags flags;
    private int id;
    private boolean safe;

    public Joke(int i, String shortJoke) {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String joke;
        private Flags flags;
        private int id;
        private boolean safe;

        private Builder() {
        }

        public Builder joke(String joke) {
            this.joke = joke;
            return this;
        }

        public Builder flags(Flags flags) {
            this.flags = flags;
            return this;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder safe(boolean safe) {
            this.safe = safe;
            return this;
        }

        public Joke build() {
            Joke joke = new Joke();
            joke.joke = this.joke;
            joke.flags = this.flags;
            joke.id = this.id;
            joke.safe = this.safe;
            return joke;
        }
        public Builder flagsWithBooleans(boolean sexist, boolean explicit) {
            this.flags = new Flags(sexist, explicit);
            return this;
        }
    }
}
