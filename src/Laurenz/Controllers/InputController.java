package Laurenz.Controllers;

import Laurenz.Controllers.MIPSModules.OpcodeMaker;
import Laurenz.Controllers.MIPSModules.Parser;
import Laurenz.Models.Instruction;
import Laurenz.Views.MainWindow;
import Laurenz.Views.Panels.CodePanel;
import Laurenz.Views.Panels.InputPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InputController
{
    /* Main Stuff */
    private MainWindow mw;
    Parser parser;
    /* Input from text box */
    private ArrayList<Instruction> instructions;
    private String input;
    /* Controller */
    private InputController inputController;
    private CodeController codeController;
    /* Views */
    private InputPanel inputPanelView;
    private CodePanel codePanelView;
    /* Button */
    private JButton loadBtn;

    public InputController(MainWindow mw)
    {
        this.mw         = mw;
        parser          = new Parser();

        codePanelView   = mw.getCodePanelView();
        inputPanelView  = mw.getInputPanelView();
        addButtonListeners();
    }


    public ArrayList<Instruction> updateCodeTable()
    {
        String input;
        OpcodeMaker opcodeMaker = new OpcodeMaker();


        input           = mw.getInputPanelView().getInputText();
        instructions    = parser.parse(input);

        if( instructions != null && instructions.size() >= 1 )
        {
            instructions = opcodeMaker.populateInstructionOpcodes(instructions);
        }

        return this.instructions;

    }

    public void addButtonListeners()
    {
        inputPanelView.getLoadBtn().addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    codePanelView.setCodeValues(updateCodeTable());
                    codePanelView.getjPanel().setBackground(Color.GREEN);
                    inputPanelView.getMainPanel().setBackground(Color.GREEN);
                    inputPanelView.getLoadBtn().setBackground(Color.CYAN);
                }
                catch(Exception x)
                {
                }
            }
        });
    }


}
