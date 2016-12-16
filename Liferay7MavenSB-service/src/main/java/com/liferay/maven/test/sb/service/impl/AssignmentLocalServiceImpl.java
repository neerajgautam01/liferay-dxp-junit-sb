/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.maven.test.sb.service.impl;

import java.util.List;

import com.liferay.maven.test.sb.model.Assignment;
import com.liferay.maven.test.sb.service.base.AssignmentLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.DateUtil;

import aQute.bnd.annotation.ProviderType;

/**
 * The implementation of the assignment local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link com.liferay.maven.test.sb.service.AssignmentLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssignmentLocalServiceBaseImpl
 * @see com.liferay.maven.test.sb.service.AssignmentLocalServiceUtil
 */
@ProviderType
public class AssignmentLocalServiceImpl extends AssignmentLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link
	 * com.liferay.training.space.gradebook.service.AssignmentLocalServiceUtil}
	 * to access the assignment local service.
	 */
	@Override
	public Assignment deleteAssignment(Assignment assignment) {

		try {
			resourceLocalService.deleteResource(assignment.getCompanyId(), Assignment.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL, assignment.getAssignmentId());
			return super.deleteAssignment(assignment);
		} catch (PortalException e) {
			throw new SystemException(e);
		}
	}

	public Assignment addAssignment(Assignment newAssignment, ServiceContext serviceContext) throws PortalException {

		long assignmentId = counterLocalService.increment(Assignment.class.getName());

		long companyId = serviceContext.getCompanyId();
		long groupId = serviceContext.getScopeGroupId();
		long userId = serviceContext.getUserId();

		User user = userLocalService.getUser(userId);

		Assignment assignment = assignmentLocalService.createAssignment(assignmentId);

		assignment.setAssignmentId(assignmentId);
		assignment.setCompanyId(companyId);
		assignment.setGroupId(groupId);
		assignment.setCreateDate(DateUtil.newDate());
		assignment.setUserId(userId);
		assignment.setUserName(user.getFullName());

		assignment.setTitleMap(newAssignment.getTitleMap());
		assignment.setDueDate(newAssignment.getDueDate());
		assignment.setDescription(newAssignment.getDescription());

		resourceLocalService.addModelResources(assignment, serviceContext);

		return assignment;
	}

	@Override
	public Assignment addAssignment(Assignment assignment) {
		throw new UnsupportedOperationException("please use instead addAssignment(Assignment, ServiceContext)");
	}

	public List<Assignment> getAssignmentsByGroupId(long groupId) {

		return assignmentPersistence.findByGroupId(groupId);
	}

	public List<Assignment> getAssignmentsByGroupId(long groupId, int start, int end) {

		return assignmentPersistence.findByGroupId(groupId, start, end);
	}

	public int getAssignmentsCountByGroupId(long groupId) {

		return assignmentPersistence.countByGroupId(groupId);
	}
}