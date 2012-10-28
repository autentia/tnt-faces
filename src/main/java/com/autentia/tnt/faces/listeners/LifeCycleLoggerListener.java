/**
 * Copyright 2012 Autentia Real Business Solutions S.L.
 * 
 * This file is part of the code that develops in Autentia.
 * 
 * This code is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 */

package com.autentia.tnt.faces.listeners;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autentia.tnt.faces.converters.SelectItemsConverter;

public class LifeCycleLoggerListener implements PhaseListener {
	
	private static final long serialVersionUID = -2504278325379445246L;

	private static final Logger LOG = LoggerFactory.getLogger(SelectItemsConverter.class);
	
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    public void beforePhase(PhaseEvent event) {
    	LOG.trace("BeforePhase: {}",event.getPhaseId());
    }

    public void afterPhase(PhaseEvent event) {
    	LOG.trace("AfterPhase: {}", event.getPhaseId());
    }

}

