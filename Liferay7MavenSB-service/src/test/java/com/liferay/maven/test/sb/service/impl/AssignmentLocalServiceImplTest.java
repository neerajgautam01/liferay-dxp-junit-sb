package com.liferay.maven.test.sb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.maven.test.sb.model.Assignment;
import com.liferay.maven.test.sb.service.AssignmentLocalService;
import com.liferay.maven.test.sb.service.persistence.AssignmentPersistence;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.DateUtil;

@RunWith(MockitoJUnitRunner.class)
public class AssignmentLocalServiceImplTest {

	private static long COMPANY_ID = 20116L;
	private static long GROUP_ID = 20147L;
	private static long USER_ID = 20164L;
	private static long ASSIGNMENT_ID = 1009L;
	private static String USER_NAME = "Liferay Consultant";

	AssignmentLocalServiceImpl impl = new AssignmentLocalServiceImpl();

	private void createAssignmentServiceImplData() throws PortalException {

		// Mock and assert counterLocalService
		CounterLocalService counterLocalService = Mockito.mock(CounterLocalService.class);
		Mockito.when(counterLocalService.increment(Assignment.class.getName())).thenReturn(ASSIGNMENT_ID);
		impl.setCounterLocalService(counterLocalService);

		// Mock User object to be used/returned by userLocalService
		User user = Mockito.mock(User.class);
		user.setUserId(USER_ID);
		user.setFirstName("Liferay");
		user.setLastName("Consultant");
		Mockito.when(user.getFullName()).thenReturn(USER_NAME);

		// Mock userLocalService
		UserLocalService userLocalService = Mockito.mock(UserLocalService.class);
		Mockito.when(userLocalService.getUser(USER_ID)).thenReturn(user);
		impl.setUserLocalService(userLocalService);

		Assignment assignment = createAssignment();
		List<Assignment> assignmentList = getAssignmentsList();

		// Mock assignmentLocalService
		AssignmentLocalService assignmentLocalService = Mockito.mock(AssignmentLocalService.class);
		Mockito.when(assignmentLocalService.createAssignment(ASSIGNMENT_ID)).thenReturn(assignment);
		Mockito.when(assignmentLocalService.getAssignmentsByGroupId(GROUP_ID)).thenReturn(assignmentList);
		Mockito.when(assignmentLocalService.getAssignmentsCountByGroupId(GROUP_ID)).thenReturn(1);
		Mockito.when(assignmentLocalService.addAssignment(assignment)).thenThrow(UnsupportedOperationException.class);
		impl.setAssignmentLocalService(assignmentLocalService);

		// Mock assignmentPersistence to be used by all other tests.
		AssignmentPersistence assignmentPersistence = Mockito.mock(AssignmentPersistence.class);
		Mockito.when(assignmentPersistence.countAll()).thenReturn(1);
		Mockito.when(assignmentPersistence.countByGroupId(GROUP_ID)).thenReturn(1);
		Mockito.when(assignmentPersistence.findByGroupId(GROUP_ID)).thenReturn(assignmentList);
		impl.setAssignmentPersistence(assignmentPersistence);

		// Mock resourceLocalService
		ResourceLocalService resourceLocalService = Mockito.mock(ResourceLocalService.class);
		impl.setResourceLocalService(resourceLocalService);

	}

	private ServiceContext getServiceContext() {

		// create ServiceContext object
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setCompanyId(COMPANY_ID);
		serviceContext.setScopeGroupId(GROUP_ID);
		serviceContext.setUserId(USER_ID);
		return serviceContext;
	}

	private Assignment createAssignment() {

		// Mock assignment object
		Assignment assignment = Mockito.mock(Assignment.class);
		assignment.setNew(true);
		assignment.setPrimaryKey(ASSIGNMENT_ID);
		assignment.setUuid("efef3911-e3cb-bd00-3e35-960359d0b936");
		assignment.setCompanyId(COMPANY_ID);
		assignment.setAssignmentId(ASSIGNMENT_ID);
		assignment.setGroupId(GROUP_ID);
		assignment.setCreateDate(DateUtil.newDate());
		assignment.setUserId(USER_ID);
		assignment.setUserName(USER_NAME);
		return assignment;
	}

	private List<Assignment> getAssignmentsList() {

		List<Assignment> assignments = new ArrayList<Assignment>();
		Assignment assignment = createAssignment();
		assignments.add(assignment);
		return assignments;
	}


	@Test
	public void addAssignmentTest() throws PortalException {
		createAssignmentServiceImplData();
		Assert.assertNotNull(impl.addAssignment(createAssignment(), getServiceContext()));
	}

	@Test
	public void getCountByGroupIdTest() throws PortalException {
		createAssignmentServiceImplData();
		Assert.assertEquals(1, impl.getAssignmentsCountByGroupId(GROUP_ID));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void addAssignmentExceptionTest() {
		impl.addAssignment(createAssignment());
		Assert.fail("please use instead addAssignment(Assignment, ServiceContext)");
	}
	
	@Test
	public void getZeroCountAssignmentTest() {
		AssignmentPersistence assignmentPersistence = Mockito.mock(AssignmentPersistence.class);
		Mockito.when(assignmentPersistence.countByGroupId(GROUP_ID)).thenReturn(0);
		impl.setAssignmentPersistence(assignmentPersistence);
		
		Assert.assertEquals(impl.getAssignmentsCountByGroupId(GROUP_ID), 0);
	}

}
