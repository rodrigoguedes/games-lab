using System.Collections;
using UnityEngine;
using UnityEngine.UI;

public class TextController : MonoBehaviour {

	public Text text;

//	private enum Status {
//		cell, mirror, sheets_0, lock_0, cell_mirror, sheets_1, lock_1, corridor_0, stairs_0, stairs_1,
//		stairs_2, courtyard, floor, corridor_1, corridor_2, corridor_3,closet_door, in_closet
//	};

	private enum Estado {
		cela, espelho, lencol_0, fechadura_0, cela_espelho, lencol_1, fechadura_1, corredor_0, escada_0,
		escada_1, escada_2, patio, piso, corredor_1, corredor_2, corredor_3, porta_armario, no_armario
	};

	private Estado estadoAtual;

	// Use this for initialization
	void Start() {
		estadoAtual = Estado.cela;
	}
	
	// Update is called once per frame
	void Update() {

		if 		(estadoAtual == Estado.cela) 			{cela();}
		else if (estadoAtual == Estado.lencol_0) 		{lencol_0();}
		else if (estadoAtual == Estado.lencol_1) 		{lencol_1();}
		else if (estadoAtual == Estado.fechadura_0) 	{fechadura_0();}
		else if (estadoAtual == Estado.fechadura_1) 	{fechadura_1();}
		else if (estadoAtual == Estado.espelho) 		{espelho();}
		else if (estadoAtual == Estado.cela_espelho)	{cela_espelho();}
		else if (estadoAtual == Estado.corredor_0) 		{corredor_0();}
		else if (estadoAtual == Estado.escada_0) 		{escada_0();}
		else if (estadoAtual == Estado.escada_1) 		{escada_1();}
		else if (estadoAtual == Estado.escada_2) 		{escada_2();}
		else if (estadoAtual == Estado.patio) 			{patio();}
		else if (estadoAtual == Estado.piso) 			{piso();}
		else if (estadoAtual == Estado.corredor_1) 		{corredor_1();}
		else if (estadoAtual == Estado.corredor_2) 		{corredor_2();}
		else if (estadoAtual == Estado.corredor_3) 		{corredor_3();}
		else if (estadoAtual == Estado.porta_armario) 	{porta_armario();}
		else if (estadoAtual == Estado.no_armario) 		{no_armario();}

	}

	void no_armario() {
		text.text = "Dentro do armário você vê o uniforme de limpeza que parece ser de seu tamanho! "+ 
			"Parece que seu dia está melhorando.\n\n" +
			"Pressione (V) para vestir, ou (R) para retornar ao corredor";

		if 		(Input.GetKeyDown(KeyCode.V)) 	{estadoAtual = Estado.corredor_2;}
		else if (Input.GetKeyDown(KeyCode.R)) 	{estadoAtual = Estado.corredor_3;}
	}

	void porta_armario() {
		text.text = "Você olha para porta do armário, infelizmente está trancada. " +
			"Talvez você possa encontrar algo em volta que possa ajudar a abrir? \n\n" +
			"Pressione (R) para retornar ao corredor";

		if (Input.GetKeyDown(KeyCode.R)) 		{estadoAtual = Estado.corredor_0;}
	}

	void corredor_3() {
		text.text = "Você está em pé no corredor, agora convincentemente vestido como um funcionario de limpeza." +
			"Você esta confiante pela em sua liberdade.\n\n" + 
			"Pressione (E) para ir a escada, ou (R) Retirar a roupa"; 

		if 		(Input.GetKeyDown(KeyCode.E)) 	{estadoAtual = Estado.patio;}
		else if (Input.GetKeyDown(KeyCode.R))	{estadoAtual = Estado.no_armario;}
	}

	void corredor_2() {
		text.text = "De volta ao corredor, tendo recusado a vestir-se como um funcionario de limpeza.\n\n" +
			"Pressione (A) para reabrir o armário, e (E) para subir as escadas";

		if 		(Input.GetKeyDown(KeyCode.C)) 	{estadoAtual = Estado.no_armario;}
		else if (Input.GetKeyDown(KeyCode.S)) 	{estadoAtual = Estado.escada_2;}
	}

	void corredor_1() {
		text.text = "Still in the corridor. Floor still dirty. Hairclip in hand. " +
			"Now what? You wonder if that lock on the closet would succumb to " +
			"to some lock-picking?\n\n" +
			"P to Pick the lock, and S to climb the stairs";


		"Ainda no corredor. O piso ainda sujo. Com um grampo de cabelo na mão.
		"E a agora? Você quer saber se a fecharuda no amario vai abrir com um ?
		" Pressione (P) para escolher o bloqueio, e (E) para subir as escadas";


		if (Input.GetKeyDown(KeyCode.P)) 		{estadoAtual = Estado.no_armario;}
		else if (Input.GetKeyDown(KeyCode.S)) 	{estadoAtual = Estado.escada_1;}
	}

	void piso() {
		text.text = "Rummagaing around on the dirty floor, you find a hairclip.\n\n" +
			"Press R to Return to the standing, or H to take the Hairclip." ;
		if 		(Input.GetKeyDown(KeyCode.R)) 	{estadoAtual = Estado.corredor_0;}
		else if (Input.GetKeyDown(KeyCode.H)) 	{estadoAtual = Estado.corredor_1;}
	}	

	void patio() {
		text.text = "You walk through the courtyard dressed as a cleaner. " +
			"The guard tips his hat at you as you waltz past, claiming " +
			"your freedom. You heart races as you walk into the sunset.\n\n" +
			"Press P to Play again." ;
		if (Input.GetKeyDown(KeyCode.P)) 		{estadoAtual = Estado.cela;}
	}	

	void escada_0() {
		text.text = "You start walking up the stairs towards the outside light. " +
			"You realise it's not break time, and you'll be caught immediately. " +
			"You slither back down the stairs and reconsider.\n\n" +
			"Press R to Return to the corridor." ;
		if (Input.GetKeyDown(KeyCode.R)) 		{estadoAtual = Estado.corredor_0;}
	}

	void escada_1() {
		text.text = "Unfortunately weilding a puny hairclip hasn't given you the " +
			"confidence to walk out into a courtyard surrounded by armed guards!\n\n" +
			"Press R to Retreat down the stairs" ;
		if (Input.GetKeyDown(KeyCode.R)) 		{estadoAtual = Estado.corredor_1;}
	}

	void escada_2() {
		text.text = "You feel smug for picking the closet door open, and are still armed with " +
			"a hairclip (now badly bent). Even these achievements together don't give " +
			"you the courage to climb up the staris to your death!\n\n" +
			"Press R to Return to the corridor";
		if (Input.GetKeyDown(KeyCode.R)) 		{estadoAtual = Estado. corredor_2;}
	}

	void cela() {

		text.text = "Você está em uma cela de prisão, e você quer escapar. " +
		"Existem alguns lençóis sujos na cama, um espelho na parede, e a porta esta fechada pelo lado de fora.\n\n" +
			"Pressione (L) para ver o Lençol, (E) para ver o Espelho e (F) para ver a Fechadura";
		if 		(Input.GetKeyDown(KeyCode.L)) 	{estadoAtual = Estado.lencol_0;}
		else if (Input.GetKeyDown(KeyCode.E)) 	{estadoAtual = Estado.espelho;}
		else if (Input.GetKeyDown(KeyCode.F)) 	{estadoAtual = Estado.fechadura_0;}
	}

	void espelho() {
		text.text = "The dirty old mirror on the wall seems loose.\n\n" +
			"Press T to Take the mirror, or R to Return to cell" ;
		if 		(Input.GetKeyDown(KeyCode.T)) 	{estadoAtual = Estado.cela_espelho;}
		else if (Input.GetKeyDown(KeyCode.R)) 	{estadoAtual = Estado.cela;}
	}

	void cela_espelho() {
		text.text = "You are still in your cell, and you STILL want to escape! There are " +
			"some dirty sheets on the bed, a mark where the mirror was, " +
			"and that pesky door is still there, and firmly locked!\n\n" +
			"Press S to view Sheets, or L to view Lock" ;
		if 		(Input.GetKeyDown(KeyCode.S)) 	{estadoAtual = Estado.lencol_1;}
		else if (Input.GetKeyDown(KeyCode.L)) 	{estadoAtual = Estado.fechadura_1;}
	}

	void lencol_0() {
		text.text = "You can't believe you sleep in these things. Surely it's " +
			"time somebody changed them. The pleasures of prison life " +
			"I guess!\n\n" +
			"Press R to Return to roaming your cell" ;
		if (Input.GetKeyDown(KeyCode.R)) 		{estadoAtual = Estado.cela;}
	}

	void lencol_1() {
		text.text = "Holding a mirror in your hand doesn't make the sheets look " +
			"any better.\n\n" +
			"Press R to Return to roaming your cell" ;
		if (Input.GetKeyDown(KeyCode.R)) 		{estadoAtual = Estado.cela_espelho;}
	}

	void fechadura_0() {
		text.text = "This is one of those button locks. You have no idea what the " +
			"combination is. You wish you could somehow see where the dirty " +
			"fingerprints were, maybe that would help.\n\n" +
			"Press R to Return to roaming your cell" ;
		if (Input.GetKeyDown(KeyCode.R)) 		{estadoAtual = Estado.cela;}
	}

	void fechadura_1() {
		text.text = "You carefully put the mirror through the bars, and turn it round " +
			"so you can see the lock. You can just make out fingerprints around " +
			"the buttons. You press the dirty buttons, and hear a click.\n\n" +
			"Press O to Open, or R to Return to your cell" ;
		if 		(Input.GetKeyDown(KeyCode.O)) 	{estadoAtual = Estado.corredor_0;}
		else if (Input.GetKeyDown(KeyCode.R)) 	{estadoAtual = Estado.cela_espelho;}
	}

	void corredor_0() {
		text.text = "You're out of your cell, but not out of trouble." +
			"You are in the corridor, there's a closet and some stairs leading to " +
			"the courtyard. There's also various detritus on the floor.\n\n" +
			"C to view the Closet, F to inspect the Floor, and S to climb the stairs";
		if 		(Input.GetKeyDown(KeyCode.S)) 	{estadoAtual = Estado.escada_0;}
		else if (Input.GetKeyDown(KeyCode.F)) 	{estadoAtual = Estado.piso;}
		else if (Input.GetKeyDown(KeyCode.C)) 	{estadoAtual = Estado.porta_armario;}
	}

}
