/****************************************************************************
 *
 *   Copyright (c) 2016 Eike Mansfeld ecm@gmx.de. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 ****************************************************************************/

package com.comino.flight.widgets.charts.line;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.comino.flight.model.KeyFigureMetaData;
import com.emxsys.chart.extension.XYAnnotation;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.ValueAxis;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class DashBoardAnnotation  implements XYAnnotation {

	private  GridPane   pane 		= null;

	private  Label      header      = new Label();
	private  HLabel      min 	    = new HLabel("Min:");
	private  HLabel      max 	    = new HLabel("Max:");
	private  HLabel      delta      = new HLabel("Delta:");
	private  HLabel      avg     	= new HLabel("\u00F8:");
	private  HLabel      std     	= new HLabel("\u03C3:");

	private  VLabel      min_v       = new VLabel();
	private  VLabel      max_v 	     = new VLabel();
	private  VLabel      delta_v     = new VLabel();
	private  VLabel      avg_v       = new VLabel();
	private  VLabel      std_v     	 = new VLabel();

	private int posy;

	private DecimalFormat f = new DecimalFormat("#0.#");

	public DashBoardAnnotation(int posy) {

        this.posy = posy;
		this.pane = new GridPane();
		pane.setStyle("-fx-background-color: rgba(60.0, 60.0, 60.0, 0.85); -fx-padding:2;");
		header.setStyle("-fx-font-size: 8pt;-fx-text-fill: #A0F0A0; -fx-padding:2;");
        this.pane.setHgap(5);
        this.pane.setMinWidth(150);
		this.pane.add(header,0,0);
		GridPane.setColumnSpan(header,4);
		this.pane.addRow(1,min,min_v,max,max_v);
		this.pane.addRow(2,delta,delta_v);
		this.pane.addRow(3,avg,avg_v,std,std_v);

		DecimalFormatSymbols sym = new DecimalFormatSymbols();
		sym.setNaN("-"); f.setDecimalFormatSymbols(sym);
	}

	public void setPosY(int y) {
		this.posy = y;
	}

	public void setKeyFigure(KeyFigureMetaData kf) {
		f.applyPattern(kf.mask);
		header.setText(kf.desc1+" ["+kf.uom+"]:");
	}

	public void setMinMax(float min, float max) {
	  min_v.setValue(min); max_v.setValue(max);
	  delta_v.setValue(max-min);
	}

	public void setAvg(float avg, float std) {
		avg_v.setValue(avg); std_v.setValue(std);
	}

	@Override
	public Node getNode() {
		return pane;
	}

	@Override
	public void layoutAnnotation(ValueAxis xAxis, ValueAxis yAxis) {
		pane.setLayoutX(10);
		pane.setLayoutY(posy);
	}

	private class VLabel extends Label {

		public VLabel() {
			super();
			setAlignment(Pos.CENTER_RIGHT);
			setMinWidth(35);
			setStyle("-fx-font-size: 8pt;-fx-text-fill: #D0D0D0; -fx-padding:2;");
		}

		public void setValue(float val) {
			setText(f.format(val));
		}
	}

	private class HLabel extends Label {

		public HLabel(String text) {
			super(text);
			setAlignment(Pos.CENTER_LEFT);
			setMinWidth(30);
			setStyle("-fx-font-size: 8pt;-fx-text-fill: #D0D0D0; -fx-padding:2;");
		}

	}

}
