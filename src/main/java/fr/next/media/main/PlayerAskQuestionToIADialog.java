package fr.next.media.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.human.MedPerson;
import fr.next.media.human.MedRoom;
import fr.next.media.human.MedWeapon;

public class PlayerAskQuestionToIADialog extends JDialog {

	private JLabel nomLabel;

	private JComboBox persCombo;
	private JComboBox weaponsCombo;
	private JComboBox roomsCombo;

	private MedPerson selectedPerson;
	private MedWeapon selectedWeapon;
	private MedRoom selectedRoom;

	public PlayerAskQuestionToIADialog(JFrame parent, String title, boolean modal, AxeOrd<AxeValue<MedPerson>> persons,
			AxeOrd<AxeValue<MedWeapon>> weapons, AxeOrd<AxeValue<MedRoom>> rooms) {
		super(parent, title, modal);
		this.setSize(550, 270);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent(persons, weapons, rooms);
	}

	private void initComponent(AxeOrd<AxeValue<MedPerson>> persons, AxeOrd<AxeValue<MedWeapon>> weapons,
			AxeOrd<AxeValue<MedRoom>> rooms) {
		JPanel content = new JPanel();
		content.setBackground(Color.white);

		persCombo = new JComboBox();
		for (AxeValue<MedPerson> per : persons.getElements()) {
			persCombo.addItem(String.valueOf(per.getValue().getPersonName()));
		}

		weaponsCombo = new JComboBox();
		for (AxeValue<MedWeapon> weapon : weapons.getElements()) {
			weaponsCombo.addItem(String.valueOf(weapon.getValue().getWeaponName()));
		}

		roomsCombo = new JComboBox();
		for (AxeValue<MedRoom> room : rooms.getElements()) {
			roomsCombo.addItem(String.valueOf(room.getValue().getRoomName()));
		}

		content.add(new JLabel("Personnage"));
		content.add(persCombo);
		content.add(new JLabel("Arme"));
		content.add(weaponsCombo);
		content.add(new JLabel("Pièce"));
		content.add(roomsCombo);
		JButton okBouton = new JButton("OK");
		content.add(okBouton);

		okBouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				for (AxeValue<MedPerson> r : persons.getElements()) {
					if (String.valueOf(r.getValue().getPersonName()).equals((String) persCombo.getSelectedItem())) {
						selectedPerson = r.getValue();
						break;
					}

				}

				for (AxeValue<MedWeapon> r : weapons.getElements()) {
					if (String.valueOf(r.getValue().getWeaponName()).equals((String) roomsCombo.getSelectedItem())) {
						selectedWeapon = r.getValue();
						break;
					}
				}

				for (AxeValue<MedRoom> r : rooms.getElements()) {
					if (String.valueOf(r.getValue().getRoomName()).equals((String) roomsCombo.getSelectedItem())) {
						selectedRoom = r.getValue();
						break;
					}
				}

				setVisible(false);
			}
		});

		this.getContentPane().add(content, BorderLayout.CENTER);
	}

	public MedPerson getPerson() {
		return selectedPerson;
	}
	
	public MedWeapon getWeapon() {
		return selectedWeapon;
	}
	
	public MedRoom getRoom() {
		return selectedRoom;
	}
}
