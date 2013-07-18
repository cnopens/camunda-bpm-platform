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
package org.camunda.bpm.engine.rest.sub.identity.impl;

import javax.ws.rs.core.Response.Status;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.rest.dto.identity.GroupDto;
import org.camunda.bpm.engine.rest.exception.InvalidRequestException;
import org.camunda.bpm.engine.rest.sub.identity.GroupMembersResource;
import org.camunda.bpm.engine.rest.sub.identity.GroupResource;

/**
 * @author Daniel Meyer
 *
 */
public class GroupResourceImpl extends AbstractIdentityResource implements GroupResource {
  
  public GroupResourceImpl(ProcessEngine processEngine, String groupId) {
    super(processEngine, groupId);
  }

  public GroupDto getGroup() {
    
    Group dbGroup = findGroupObject();    
    if(dbGroup == null) {
      throw new InvalidRequestException(Status.NOT_FOUND, "Group with id " + resourceId + " does not exist");
    }
    
    return GroupDto.fromGroup(dbGroup);
  }


  public void updateGroup(GroupDto group) {    
    ensureNotReadOnly();
    
    Group dbGroup = findGroupObject();    
    if(dbGroup == null) {
      throw new InvalidRequestException(Status.NOT_FOUND, "Group with id " + resourceId + " does not exist");
    }
    
    group.update(dbGroup);        
    try {
      identityService.saveGroup(dbGroup);
      
    } catch (ProcessEngineException e) {
      throw new InvalidRequestException(Status.INTERNAL_SERVER_ERROR, "Exception while updating group "+resourceId+": "+e.getMessage());
    }
  }
  

  public void deleteGroup() {    
    ensureNotReadOnly();
    
    try {
      identityService.deleteGroup(resourceId);
    } catch(ProcessEngineException e) {
      throw new InvalidRequestException(Status.INTERNAL_SERVER_ERROR, "Exception while deleting group "+resourceId+": "+e.getMessage());
    }
        
  }

  public GroupMembersResource getGroupMembersResource() {
    return new GroupMembersResourceImpl(processEngine, resourceId);
  }
  
  protected Group findGroupObject() {
    try {      
      return identityService.createGroupQuery()
          .groupId(resourceId)
          .singleResult();      
    } catch(ProcessEngineException e) {
      throw new InvalidRequestException(Status.INTERNAL_SERVER_ERROR, "Exception while performing group query: "+e.getMessage());
    }
  }


}