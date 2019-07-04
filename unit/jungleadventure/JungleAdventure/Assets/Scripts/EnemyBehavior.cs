﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyBehavior : MonoBehaviour {

	private Rigidbody2D rb;
	private Transform tr;
	private Animator an;

	public Transform verificaChao;
	public Transform verificaParede;

	private bool estaNoChao;
	private bool estaNaParede;
	private bool viradoParaDireita;

	public float velocidade;
	public float raioValidaChao;
	public float raioValidaParede;

	public LayerMask solido;

	// Use this for initialization
	void Awake () {

		rb = GetComponent<Rigidbody2D> ();
		tr = GetComponent<Transform> ();
		an = GetComponent<Animator> ();

		viradoParaDireita = true;

	}
	
	// Update is called once per frame
	void FixedUpdate () {
		
		EnemyMovements ();
	}

	void EnemyMovements() {

		estaNoChao = Physics2D.OverlapCircle (verificaChao.position, raioValidaChao, solido);
		estaNaParede = Physics2D.OverlapCircle (verificaParede.position, raioValidaParede, solido);

		if ((!estaNoChao || estaNaParede) && viradoParaDireita) {
			
			Flip ();
		} else if ((!estaNoChao || estaNaParede) && !viradoParaDireita) {
			Flip ();
		}
			

		if (estaNoChao) {
			rb.velocity = new Vector2 (velocidade, rb.velocity.y);
		}
	}

	void Flip() {
		
		viradoParaDireita = !viradoParaDireita;
		tr.localScale = new Vector2 (-tr.localScale.x, tr.localScale.y);

		velocidade *= -1;
	}

	void OnDrawGizmosSelected() {

		Gizmos.color = Color.red;

		Gizmos.DrawWireSphere (verificaChao.position, raioValidaChao);
		Gizmos.DrawWireSphere (verificaParede.position, raioValidaParede);

	}

}
