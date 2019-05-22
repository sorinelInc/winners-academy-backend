package org.vaadin.paul.spring.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.paul.spring.model.Match;
import org.vaadin.paul.spring.model.Result;

import java.util.Arrays;
import java.util.Set;


public class EditMatchDialog extends Dialog {

    public EditMatchDialog(Match currentMatch){

//        DatePicker date = new DatePicker("Match Date");
        TextField name = new TextField("Match Name");
        NumberField odds = new NumberField("Odds");
        TextField tips = new TextField("Tips");

        ComboBox<Result> result = new ComboBox<>("Result", Arrays.asList(Result.WON, Result.LOST, Result.NOT_DECIDED));

        Button save = new Button("Save");
        Button close = new Button("Cancel");
        close.addClickListener(event -> this.close());

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, close);

        if (currentMatch != null ){
            name.setValue(currentMatch.getName());
            odds.setValue(currentMatch.getOdds());
            result.setValue(currentMatch.getResult());
            tips.setValue(currentMatch.getTips());
        }

        save.addClickListener(event -> this.save());

        VerticalLayout editable = new VerticalLayout();
        editable.add(name, odds, result, tips);
        add(editable, actions);
    }

    private void save(){

        this.close();
    }
}
