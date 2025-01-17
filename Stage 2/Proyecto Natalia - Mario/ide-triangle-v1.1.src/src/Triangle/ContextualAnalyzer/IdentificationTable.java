/*
 * @(#)IdentificationTable.java                2.1 2003/10/07
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


package Triangle.ContextualAnalyzer;

import Triangle.AbstractSyntaxTrees.Declaration;
import java.util.Stack;

public final class IdentificationTable {

  private int level;
  private IdEntry latest;  // Desde ac� se usa la lista enlazada
  
  //local declarations
  // new variables declared for local method
  private Stack<IdEntry> stackPrivate = new Stack<IdEntry>();
  private Stack<IdEntry> stackPublic = new Stack<IdEntry>();

  public IdentificationTable () {
    level = 0;   // En el nivel 0, se establece el ambiente estandar, var.indent predef.
    latest = null;
  }


  // Opens a new level in the identification table, 1 higher than the
  // current topmost level.

  public void openScope () {

    level ++;
  }

  // Closes the topmost level in the identification table, discarding
  // all entries belonging to that level.
  // Quita todo lo del nivel
  public void closeScope () {

    IdEntry entry, local;

    // Presumably, idTable.level > 0.
    entry = this.latest;
    while (entry.level == this.level) {
      local = entry;
      entry = local.previous;
    }
    this.level--;  //reestablecer nivel 
    this.latest = entry;
  }

  // Makes a new entry in the identification table for the given identifier
  // and attribute. The new entry belongs to the current level.
  // duplicated is set to to true iff there is already an entry for the
  // same identifier at the current level.

  public void enter (String id, Declaration attr) {

    IdEntry entry = this.latest;
    boolean present = false, searching = true;

    // Check for duplicate entry ...
    while (searching) {
      if (entry == null || entry.level < this.level){
        searching = false;
      }
      else if (entry.id.equals(id)) {
        present = true;
        searching = false;
       } else
       entry = entry.previous;
    }

    attr.duplicated = present;
    // Add new entry ...
    entry = new IdEntry(id, attr, this.level, this.latest);
    this.latest = entry;
  }

  // Finds an entry for the given identifier in the identification table,
  // if any. If there are several entries for that identifier, finds the
  // entry at the highest level, in accordance with the scope rules.
  // Returns null iff no entry is found.
  // otherwise returns the attribute field of the entry found.

  public Declaration retrieve (String id) {
    // Busca y retorna el AST que le corresponde 
    IdEntry entry;
    Declaration attr = null;
    boolean present = false, searching = true;

    entry = this.latest;
    while (searching) {
      if (entry == null){
        searching = false;
//        attr = retrieveLocally ? retrieveLocally(id) : null;
    }
      else if (entry.id.equals(id)) {
        present = true;
        searching = false;
        attr = entry.attr;
      } else
        entry = entry.previous;
    }

    return attr;
  }
  

    ///////////////////////////////////////////////////////////////////////////////
    //
    //  Yosua Andres Blanco Diaz
    //  Dylan Stef Torres Walker 
    //  Johel Mora Calderon
    //  Adition for local implementation
    //
    ///////////////////////////////////////////////////////////////////////////////
  
  public void pushToPrivateStack(){
      IdEntry privateID = this.latest;
      
      stackPrivate.push(privateID);
  }
  
  public void pushToPublicStack(){
      IdEntry publicID = this.latest;
      
      stackPublic.push(publicID);
  }
  
  public void closePrivateStack(){
      IdEntry id = this.latest;
      IdEntry privateId = stackPrivate.pop();
      IdEntry publicId = stackPublic.pop();
      
      while(id.previous  != privateId){
          id = id.previous;
          
      }
      
      id.previous = publicId;
 
  }


}
