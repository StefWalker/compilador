/*
 * @(#)Vname.java                        2.1 2003/10/07
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

// van a hacer variables por omision y no vamos a saber su tipo
// Pero se maneja en el contextual
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public abstract class Vname extends AST {

  public Vname (SourcePosition thePosition) {
    super (thePosition);
    variable = false;
    type = null;
  }

  //Esto es para generador de codigo
  public boolean variable, indexed;
  public int offset;
  public TypeDenoter type;
}