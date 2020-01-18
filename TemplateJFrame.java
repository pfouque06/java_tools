package javaTools;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JTree;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JProgressBar;
import javax.swing.JFormattedTextField;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class TemplateJFrame {

	private JFrame frame;
	private JTable table;
	private JTextField txtJtextfield;
	private final Action action = new SwingAction();
	private final Action action_1 = new SwingAction_1();
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TemplateJFrame window = new TemplateJFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TemplateJFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 525, 525);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JCheckBox checkBox_2 = new JCheckBox("Auto Mode");
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("JToggleButton");
		tglbtnNewToggleButton.setEnabled(false);
		tglbtnNewToggleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		tglbtnNewToggleButton.setAction(action);
		
		JButton btnNewButton = new JButton("JButton");
		
		JTextPane txtpnJtextpane = new JTextPane();
		txtpnJtextpane.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "JTextPane", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(51, 51, 51)));
		txtpnJtextpane.setText("JTextPane\n2\n3\n4\n5\n6\n7\n8\n9\n10");
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Title", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		spinner_1.setToolTipText("ToolTip");
		
		table = new JTable();
		table.setFont(new Font("Segment14", Font.BOLD, 12));
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setShowHorizontalLines(false);
		table.setShowGrid(false);
		table.setToolTipText("ToolTip");
		table.setShowVerticalLines(false);
		table.setRowSelectionAllowed(false);
		table.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"1", "2", "3", "4", "5", "6"},
				{"7", "8", "9", "10", "11", "12"},
				{"13", "14", "15", "16", "17", "18"},
				{"19", "20", "21", "22", "23", "24"},
				{"25", "26", "27", "28", "29", "30"},
				{"31", "32", "33", "34", "35", "36"},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		table.getColumnModel().getColumn(5).setPreferredWidth(72);
		
		txtJtextfield = new JTextField();
		txtJtextfield.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "JTextField", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		txtJtextfield.setText("JTextField");
		txtJtextfield.setColumns(10);
		
		JTree tree = new JTree();
		tree.setToolTipText("toolTip");
		tree.setName("myTree");
		tree.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JEditorPane editorPane = new JEditorPane();
		
		JTextArea txtrJtextarea = new JTextArea();
		txtrJtextarea.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "JTextArea", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		txtrJtextarea.setText("JTextArea\n2\n3\n4\n5\n6\n7\n8\n9\n10");
		
		JProgressBar progressBar = new JProgressBar();
		
		JFormattedTextField frmtdtxtfldJformattedtext = new JFormattedTextField();
		frmtdtxtfldJformattedtext.setText("JFormattedText");
		
		JLabel lblNewLabel = new JLabel("Label");
		lblNewLabel.setBorder(new TitledBorder(null, "Label", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setToolTipText("ToolTip");
		spinner_2.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Title", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		
		JCheckBox checkBox_1 = new JCheckBox("Auto Mode");
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Radio buttons", TitledBorder.LEADING, TitledBorder.TOP, null, Color.DARK_GRAY));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		JCheckBox checkBox_3 = new JCheckBox("Auto Mode");
		
		JSlider slider = new JSlider();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGap(16)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(txtJtextfield, Alignment.LEADING, 221, 241, Short.MAX_VALUE)
								.addComponent(editorPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txtrJtextarea, GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
								.addComponent(frmtdtxtfldJformattedtext, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE))
							.addGap(18))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(table, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(slider, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tree, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(txtpnJtextpane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(checkBox_1, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
									.addComponent(checkBox_2))
								.addComponent(checkBox_3, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(tglbtnNewToggleButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton)))
					.addContainerGap(250, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(12, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtJtextfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtrJtextarea, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(txtpnJtextpane, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(tree, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(6)
									.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(panel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(101)
									.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(179)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(checkBox_1)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBox_2)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(checkBox_3))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(frmtdtxtfldJformattedtext, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(table, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(tglbtnNewToggleButton)
							.addComponent(btnNewButton))
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(84))
		);
		
		JRadioButton radioButton_1 = new JRadioButton("New radio button");
		panel.add(radioButton_1);
		buttonGroup.add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("New radio button");
		panel.add(radioButton_2);
		buttonGroup.add(radioButton_2);
		frame.getContentPane().setLayout(groupLayout);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "SwingAction_1");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
