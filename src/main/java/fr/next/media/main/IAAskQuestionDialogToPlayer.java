package fr.next.media.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.next.media.human.MedPerson;
import fr.next.media.human.MedRoom;
import fr.next.media.human.MedWeapon;

public class IAAskQuestionDialogToPlayer extends JDialog {

	private JLabel nomLabel;

	private MedPerson person;
	private MedWeapon weapon;
	private MedRoom room;
	
	private int indexValid = 0;

	public IAAskQuestionDialogToPlayer(JFrame parent, String title, boolean modal, MedPerson person, MedWeapon weapon, MedRoom room) {
		super(parent, title, modal);
		this.setSize(550, 270);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent(person, weapon, room);
	}

	private void initComponent(MedPerson person, MedWeapon weapon, MedRoom room) {
		JPanel content = new JPanel();
		content.setBackground(Color.white);

		JButton validNo = new JButton("Aucune");
		JButton validPerson = new JButton(String.valueOf(person.getPersonName()));
		JButton validWeapon = new JButton(String.valueOf(weapon.getWeaponName()));
		JButton validRoom = new JButton(String.valueOf(room.getRoomName()));
		
		content.add(validNo);
		content.add(validPerson);
		content.add(validWeapon);
		content.add(validRoom);

		validNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				indexValid = 0;
				setVisible(false);
			}
		});
		
		validPerson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				indexValid = 1;
				setVisible(false);
			}
		});
		
		validWeapon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				indexValid = 2;
				setVisible(false);
			}
		});
		
		validRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				indexValid = 3;
				setVisible(false);
			}
		});

		this.getContentPane().add(content, BorderLayout.CENTER);
	}

	public int getIndexChoice() {
		return indexValid;
	}
	
	
}
