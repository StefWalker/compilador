/*
 * @(#)IdEntry.java                        2.1 2003/10/07
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */


//Los elementos de la tabla de identificacion se encuentran aqu�
// Toda cosa que se declara tiene un id, un nivel, y un attrib. 
// El previo hace de esto una lista simplemente enlazada
package Triangle.ContextualAnalyzer;

import Triangle.AbstractSyntaxTrees.Declaration;

public class IdEntry {

  protected String id;
  protected Declaration attr;
  protected int level;
  protected IdEntry previous;

  IdEntry (String id, Declaration attr, int level, IdEntry previous) {
    this.id = id;
    this.attr = attr;
    this.level = level;
    this.previous = previous;
  }

}
