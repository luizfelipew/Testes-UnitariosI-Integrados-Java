package br.ce.wendt.matchers;

import java.util.Calendar;

public class MatchersProprios {

    public static DiaSemanaMatcher caiEm(Integer diaSemada){
        return new DiaSemanaMatcher(diaSemada);
    }

    public static DiaSemanaMatcher caiNumaSegunda(){
        return new DiaSemanaMatcher(Calendar.MONDAY);
    }

    public static DataDiferencaDiasMatcher ehHojeComDiferenciaDias(Integer qtdDias){
        return new DataDiferencaDiasMatcher(qtdDias);

    }

    public static DataDiferencaDiasMatcher ehHoje(){
        return new DataDiferencaDiasMatcher(0);

    }

}
