//
// ShadowMenuIterator.java
//

/*
ImageJ software for multidimensional image processing and analysis.

Copyright (c) 2010, ImageJDev.org.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the names of the ImageJDev.org developers nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
*/

package imagej.ext.menu;

import imagej.ext.module.ModuleInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Recursive iterator for {@link ShadowMenu} hierarchies.
 * 
 * @author Curtis Rueden
 */
public class ShadowMenuIterator implements Iterator<ModuleInfo> {

	private final ShadowMenu rootMenu;
	private final List<ShadowMenuIterator> childIterators;
	private int index = -1;

	public ShadowMenuIterator(final ShadowMenu rootMenu) {
		this.rootMenu = rootMenu;
		final List<ShadowMenu> children = rootMenu.getChildren();
		childIterators = new ArrayList<ShadowMenuIterator>();
		for (final ShadowMenu child : children) {
			childIterators.add(new ShadowMenuIterator(child));
		}
	}

	@Override
	public boolean hasNext() {
		return index < childIterators.size();
	}

	@Override
	public ModuleInfo next() {
		if (!hasNext()) throw new NoSuchElementException();
		if (index < 0) {
			index++;
			return rootMenu.getModuleInfo();
		}
		final ShadowMenuIterator iter = childIterators.get(index);
		final ModuleInfo next = iter.next();
		if (!iter.hasNext()) index++;
		return next;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
