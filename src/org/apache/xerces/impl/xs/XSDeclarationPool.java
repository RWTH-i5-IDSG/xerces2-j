/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2001, 2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Xerces" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 2001, International
 * Business Machines, Inc., http://www.apache.org.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;

/**
 * This class is pool that enables caching of XML Schema declaration objects.
 * Before a compiled grammar object is garbage collected,
 * the implementation will add all XML Schema component
 * declarations to the pool.
 * Note: The cashing mechanism is not implemented yet.
 * 
 * @author Elena Litani, IBM
 * @version $Id$
 */
public final class XSDeclarationPool {
    /** Chunk shift (8). */
    private static final int CHUNK_SHIFT = 8; // 2^8 = 256

    /** Chunk size (1 << CHUNK_SHIFT). */
    private static final int CHUNK_SIZE = 1 << CHUNK_SHIFT;

    /** Chunk mask (CHUNK_SIZE - 1). */
    private static final int CHUNK_MASK = CHUNK_SIZE - 1;

    /** Initial chunk count (). */
    private static final int INITIAL_CHUNK_COUNT = (1 << (10 - CHUNK_SHIFT)); // 2^10 = 1k

    /** Element declaration pool*/
    private XSElementDecl fElementDecl[][] = new XSElementDecl[INITIAL_CHUNK_COUNT][];
    private int fElementDeclIndex = 0;

    /** Particle declaration pool */
    private XSParticleDecl fParticleDecl[][] = new XSParticleDecl[INITIAL_CHUNK_COUNT][];
    private int fParticleDeclIndex = 0;

    /** Attribute declaration pool */
    private XSAttributeDecl fAttrDecl[][] = new XSAttributeDecl[INITIAL_CHUNK_COUNT][];
    private int fAttrDeclIndex = 0;

    /** ComplexType declaration pool */
    private XSComplexTypeDecl fCTDecl[][] = new XSComplexTypeDecl[INITIAL_CHUNK_COUNT][];
    private int fCTDeclIndex = 0;

    /** SimpleType declaration pool */
    private XSSimpleTypeDecl fSTDecl[][] = new XSSimpleTypeDecl[INITIAL_CHUNK_COUNT][];
    private int fSTDeclIndex = 0;

    /** AttributeUse declaration pool */
    private XSAttributeUse fAttributeUse[][] = new XSAttributeUse[INITIAL_CHUNK_COUNT][];
    private int fAttributeUseIndex = 0;

    public final  XSElementDecl getElementDecl(){
        int     chunk       = fElementDeclIndex >> CHUNK_SHIFT;
        int     index       = fElementDeclIndex &  CHUNK_MASK;
        ensureElementDeclCapacity(chunk);
        if (fElementDecl[chunk][index] == null) {
            fElementDecl[chunk][index] = new XSElementDecl();
        } else {
            fElementDecl[chunk][index].reset();
        }
        fElementDeclIndex++;
        return fElementDecl[chunk][index];
    }

    public final XSAttributeDecl getAttributeDecl(){
        int     chunk       = fAttrDeclIndex >> CHUNK_SHIFT;
        int     index       = fAttrDeclIndex &  CHUNK_MASK;
        ensureAttrDeclCapacity(chunk);
        if (fAttrDecl[chunk][index] == null) {
            fAttrDecl[chunk][index] = new XSAttributeDecl();
        } else {
            fAttrDecl[chunk][index].reset();
        }
        fAttrDeclIndex++;
        return fAttrDecl[chunk][index];

    }

    public final XSAttributeUse getAttributeUse(){
        int     chunk       = fAttributeUseIndex >> CHUNK_SHIFT;
        int     index       = fAttributeUseIndex &  CHUNK_MASK;
        ensureAttributeUseCapacity(chunk);
        if (fAttributeUse[chunk][index] == null) {
            fAttributeUse[chunk][index] = new XSAttributeUse();
        } else {
            fAttributeUse[chunk][index].reset();
        }
        fAttributeUseIndex++;
        return fAttributeUse[chunk][index];

    }
    
    public final XSComplexTypeDecl getComplexTypeDecl(){
        int     chunk       = fCTDeclIndex >> CHUNK_SHIFT;
        int     index       = fCTDeclIndex &  CHUNK_MASK;
        ensureCTDeclCapacity(chunk);
        if (fCTDecl[chunk][index] == null) {

            fCTDecl[chunk][index] = new XSComplexTypeDecl();
        } else {
            fCTDecl[chunk][index].reset();
        }
        fCTDeclIndex++;
        return fCTDecl[chunk][index];
    }

    public final XSSimpleTypeDecl getSimpleTypeDecl(){
        int     chunk       = fSTDeclIndex >> CHUNK_SHIFT;
        int     index       = fSTDeclIndex &  CHUNK_MASK;
        ensureSTDeclCapacity(chunk);
        if (fSTDecl[chunk][index] == null) {
            fSTDecl[chunk][index] = new XSSimpleTypeDecl();
        } else {
            fSTDecl[chunk][index].reset();
        }
        fSTDeclIndex++;
        return fSTDecl[chunk][index];

    } 

    public final XSParticleDecl getParticleDecl(){
        int     chunk       = fParticleDeclIndex >> CHUNK_SHIFT;
        int     index       = fParticleDeclIndex &  CHUNK_MASK;
        ensureParticleDeclCapacity(chunk);
        if (fParticleDecl[chunk][index] == null) {
            fParticleDecl[chunk][index] = new XSParticleDecl();
        } else {
            fParticleDecl[chunk][index].reset();
        }
        fParticleDeclIndex++;
        return fParticleDecl[chunk][index];
    }

    // REVISIT: do we need decl pool for group declarations, attribute group,
    //          notations?
    //          it seems like each schema would use a small number of those
    //          components, so it probably is not worth keeping those components
    //          in the pool.

    private boolean ensureElementDeclCapacity(int chunk) {
        try {
            return fElementDecl[chunk][0] == null;
        } catch (ArrayIndexOutOfBoundsException ex) {
            fElementDecl = resize(fElementDecl, fElementDecl.length * 2);
        } catch (NullPointerException ex) {
            // ignore
        }

        fElementDecl[chunk] = new XSElementDecl[CHUNK_SIZE];
        return true;
    }

    private static XSElementDecl[][] resize(XSElementDecl array[][], int newsize) {
        XSElementDecl newarray[][] = new XSElementDecl[newsize][];
        System.arraycopy(array, 0, newarray, 0, array.length);
        return newarray;
    }

    private boolean ensureParticleDeclCapacity(int chunk) {
        try {
            return fParticleDecl[chunk][0] == null;
        } catch (ArrayIndexOutOfBoundsException ex) {
            fParticleDecl = resize(fParticleDecl, fParticleDecl.length * 2);
        } catch (NullPointerException ex) {
            // ignore
        }

        fParticleDecl[chunk] = new XSParticleDecl[CHUNK_SIZE];
        return true;
    }

    private static XSParticleDecl[][] resize(XSParticleDecl array[][], int newsize) {
        XSParticleDecl newarray[][] = new XSParticleDecl[newsize][];
        System.arraycopy(array, 0, newarray, 0, array.length);
        return newarray;
    }


    private boolean ensureAttrDeclCapacity(int chunk) {
        try {
            return fAttrDecl[chunk][0] == null;
        } catch (ArrayIndexOutOfBoundsException ex) {
            fAttrDecl = resize(fAttrDecl, fAttrDecl.length * 2);
        } catch (NullPointerException ex) {
            // ignore
        }

        fAttrDecl[chunk] = new XSAttributeDecl[CHUNK_SIZE];
        return true;
    }

    private static XSAttributeDecl[][] resize(XSAttributeDecl array[][], int newsize) {
        XSAttributeDecl newarray[][] = new XSAttributeDecl[newsize][];
        System.arraycopy(array, 0, newarray, 0, array.length);
        return newarray;
    }

    private boolean ensureAttributeUseCapacity(int chunk) {
        try {
            return fAttributeUse[chunk][0] == null;
        } catch (ArrayIndexOutOfBoundsException ex) {
            fAttributeUse = resize(fAttributeUse, fAttributeUse.length * 2);
        } catch (NullPointerException ex) {
            // ignore
        }

        fAttributeUse[chunk] = new XSAttributeUse[CHUNK_SIZE];
        return true;
    }

    private static XSAttributeUse[][] resize(XSAttributeUse array[][], int newsize) {
        XSAttributeUse newarray[][] = new XSAttributeUse[newsize][];
        System.arraycopy(array, 0, newarray, 0, array.length);
        return newarray;
    }

    private boolean ensureSTDeclCapacity(int chunk) {
        try {
            return fSTDecl[chunk][0] == null;
        } catch (ArrayIndexOutOfBoundsException ex) {
            fSTDecl = resize(fSTDecl, fSTDecl.length * 2);
        } catch (NullPointerException ex) {
            // ignore
        }

        fSTDecl[chunk] = new XSSimpleTypeDecl[CHUNK_SIZE];
        return true;
    }

    private static XSSimpleTypeDecl[][] resize(XSSimpleTypeDecl array[][], int newsize) {
        XSSimpleTypeDecl newarray[][] = new XSSimpleTypeDecl[newsize][];
        System.arraycopy(array, 0, newarray, 0, array.length);
        return newarray;
    }

    private boolean ensureCTDeclCapacity(int chunk) {
        try {
            return fCTDecl[chunk][0] == null;
        } catch (ArrayIndexOutOfBoundsException ex) {
            fCTDecl = resize(fCTDecl, fCTDecl.length * 2);
        } catch (NullPointerException ex) {
            // ignore
        }

        fCTDecl[chunk] = new XSComplexTypeDecl[CHUNK_SIZE];
        return true;
    }

    private static XSComplexTypeDecl[][] resize(XSComplexTypeDecl array[][], int newsize) {
        XSComplexTypeDecl newarray[][] = new XSComplexTypeDecl[newsize][];
        System.arraycopy(array, 0, newarray, 0, array.length);
        return newarray;
    }



    public void reset(){
        fElementDeclIndex = 0;
        fParticleDeclIndex = 0;
        fSTDeclIndex = 0;
        fCTDeclIndex = 0;
        fAttrDeclIndex = 0;
    }


}
