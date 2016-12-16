create index IX_44CB7CD4 on DXP_Assignment (groupId);
create index IX_6756F774 on DXP_Assignment (resourceBlockId);
create index IX_EC8A090A on DXP_Assignment (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_DE354A0C on DXP_Assignment (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_E23EAF1 on DXP_Submission (groupId, assignmentId);
create index IX_A901B271 on DXP_Submission (studentId);