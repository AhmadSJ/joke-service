package com.ns.jokeservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Flags {
    private boolean sexist;
    private boolean explicit;

    public Flags(boolean sexist, boolean explicit) {
        this.sexist = sexist;
        this.explicit = explicit;
    }
}
