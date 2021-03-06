/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camunda.bpm.engine.impl.pvm.runtime;


import org.camunda.bpm.engine.impl.pvm.PvmProcessElement;
import org.camunda.bpm.engine.impl.pvm.PvmProcessInstance;
import org.camunda.bpm.engine.impl.pvm.PvmTransition;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityExecution;
import org.camunda.bpm.engine.impl.pvm.delegate.ExecutionListenerExecution;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.camunda.bpm.engine.impl.pvm.process.TransitionImpl;


/**
 * @author Tom Baeyens
 */
public interface InterpretableExecution extends ActivityExecution, ExecutionListenerExecution, PvmProcessInstance {

  void take(PvmTransition transition);

  void setEventName(String eventName);

  void setEventSource(PvmProcessElement element);

  Integer getExecutionListenerIndex();
  void setExecutionListenerIndex(Integer executionListenerIndex);

  ProcessDefinitionImpl getProcessDefinition();

  void setActivity(ActivityImpl activity);

  void performOperation(AtomicOperation etomicOperation);
  
  void performOperationSync(AtomicOperation executionOperation);

  boolean isScope();

  void destroy();

  void remove();

  InterpretableExecution getReplacedBy();
  void setReplacedBy(InterpretableExecution replacedBy);

  InterpretableExecution getSubProcessInstance();
  void setSubProcessInstance(InterpretableExecution subProcessInstance);

  InterpretableExecution getSuperExecution();

  void deleteCascade(String deleteReason);
  
  void deleteCascade2(String deleteReason);
  
  boolean isDeleteRoot();

  TransitionImpl getTransition();
  void setTransition(TransitionImpl object);

  void initialize();

  void setParent(InterpretableExecution parent);

  void setProcessDefinition(ProcessDefinitionImpl processDefinitionImpl);

  void setProcessInstance(InterpretableExecution processInstance);
  
  boolean isEventScope();
  
  void setEventScope(boolean isEventScope);
  
  ProcessInstanceStartContext getProcessInstanceStartContext();
  
  void disposeProcessInstanceStartContext();

}