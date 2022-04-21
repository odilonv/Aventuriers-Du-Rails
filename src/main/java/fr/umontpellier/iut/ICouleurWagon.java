package fr.umontpellier.iut;

import fr.umontpellier.iut.rails.CouleurWagon;

public interface ICouleurWagon {
    static ICouleurWagon[] values() {
        return CouleurWagon.values();
    }
}