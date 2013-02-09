package uk.ac.ed.inf.pepa.eclipse.ui.wizards.capacityplanning;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import uk.ac.ed.inf.pepa.eclipse.core.IPepaModel;

public class SetUpOptimiserPage extends WizardPage {
  private Text text1;
  private Text text2;
  private IPepaModel model;
  private Composite container;

  public SetUpOptimiserPage(IPepaModel model) {
    super("Stochastic Search Optimisation");
    setTitle("Stochastic Search Optimisation");
    setDescription("Setting up Search Optimisation");
  }

  @Override
  public void createControl(Composite parent) {
    container = new Composite(parent, SWT.NULL);
    GridLayout layout = new GridLayout();
    container.setLayout(layout);
    layout.numColumns = 2;
    Label label1 = new Label(container, SWT.NULL);
    label1.setText("Maximum Agent Population for component:");

    
    text1 = new Text(container, SWT.BORDER | SWT.SINGLE);
    text1.setText("");
    text1.addKeyListener(new KeyListener() {

      @Override
      public void keyPressed(KeyEvent e) {
      }

      @Override
      public void keyReleased(KeyEvent e) {
        if (!text1.getText().isEmpty()) {
          setPageComplete(true);

        }
      }

    });
    
    
    Label label2 = new Label(container, SWT.NULL);
    label2.setText("Minimum Agent Population for component:");
    
    text2 = new Text(container, SWT.BORDER | SWT.SINGLE);
    text2.setText("");
    text2.addKeyListener(new KeyListener() {

      @Override
      public void keyPressed(KeyEvent e) {
      }

      @Override
      public void keyReleased(KeyEvent e) {
        if (!text2.getText().isEmpty()) {
          setPageComplete(true);

        }
      }

    });    
    
    
    Label label3 = new Label(container, SWT.NULL);
    label3.setText(" ");
    
    
    // Required to avoid an error in the system
    setControl(container);
    setPageComplete(false);

  }

  public String getText1() {
    return text1.getText();
  }
} 