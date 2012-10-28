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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

import org.junit.Test;


public class LifeCycleLoggerListenerTest{
	
    private static LifeCycleLoggerListener lifeCycleLoggerListener = new LifeCycleLoggerListener();

    @Test
    public void shouldListenAllPhases(){
    	assertEquals(PhaseId.ANY_PHASE, lifeCycleLoggerListener.getPhaseId());
    }

    @Test
    public void shouldImplementsAroundLogger(){
    	final PhaseEvent phaseEvent = mock(PhaseEvent.class);
    	lifeCycleLoggerListener.beforePhase(phaseEvent);
    	lifeCycleLoggerListener.afterPhase(phaseEvent);
    	verify(phaseEvent,times(2)).getPhaseId();
    }
    
}

