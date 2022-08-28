package com.sda.cz5;

public class Telo {

    Oko leve = new Oko("leve");
    Oko prave = new Oko("prave");

    public void zamrkej(boolean prave, boolean leve) {
        if (prave) {
            this.prave.zamrkej();
        }
        if (leve) {
            this.leve.zamrkej();
        }
    }

    private class Oko {
        private String typ;

        public Oko(String typ) {
            this.typ = typ;
        }

        public void zamrkej() {
            System.out.println("Mrkam " + typ);
        }
    }
}
