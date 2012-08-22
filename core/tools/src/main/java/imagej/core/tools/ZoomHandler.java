/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2012 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */

package imagej.core.tools;

import imagej.Priority;
import imagej.data.display.ImageDisplay;
import imagej.display.Display;
import imagej.display.event.input.KyPressedEvent;
import imagej.ext.KeyCode;
import imagej.ext.plugin.Plugin;
import imagej.tool.AbstractTool;
import imagej.tool.Tool;
import imagej.util.IntCoords;

/**
 * Handles keyboard operations that change the zoom.
 * 
 * @author Curtis Rueden
 */
@Plugin(type = Tool.class, name = "Zoom Shortcuts", alwaysActive = true,
	activeInAppFrame = true, priority = Priority.NORMAL_PRIORITY)
public class ZoomHandler extends AbstractTool {

	@Override
	public void onKeyDown(final KyPressedEvent evt) {
		final Display<?> display = evt.getDisplay();
		if (!(display instanceof ImageDisplay)) return;
		final ImageDisplay imageDisplay = (ImageDisplay) display;

		final int x = evt.getX();
		final int y = evt.getY();
		final IntCoords center = x < 0 || y < 0 ? null : new IntCoords(x, y);

		final KeyCode keyCode = evt.getCode();
		final char keyChar = evt.getCharacter();

		if (keyCode == KeyCode.EQUALS || keyCode == KeyCode.PLUS ||
			keyChar == '=' || keyChar == '+')
		{
			if (center == null) imageDisplay.getCanvas().zoomIn();
			else imageDisplay.getCanvas().zoomIn(center);
			evt.consume();
		}
		else if (keyCode == KeyCode.MINUS || keyChar == '-') {
			if (center == null) imageDisplay.getCanvas().zoomOut();
			else imageDisplay.getCanvas().zoomOut(center);
			evt.consume();
		}
	}

}
