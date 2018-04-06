package br.ce.wendt.matchers;

import br.ce.wendt.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DiaSemanaMatcher extends TypeSafeMatcher<Date> {

    private Integer diaSemana;

    public DiaSemanaMatcher(Integer diaSemana){
        this.diaSemana = diaSemana;
    }

    @Override
    public void describeTo(Description description) {
        Calendar data = Calendar.getInstance();
        data.set(Calendar.DAY_OF_MONTH, diaSemana);
        String dataExtenso = data.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
        description.appendText(dataExtenso);
    }


    @Override
    protected boolean matchesSafely(Date data) {
        return DataUtils.verificarDiaSemana(data, diaSemana);
    }


}
