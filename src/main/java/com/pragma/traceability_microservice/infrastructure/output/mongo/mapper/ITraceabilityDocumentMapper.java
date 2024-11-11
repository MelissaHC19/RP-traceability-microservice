package com.pragma.traceability_microservice.infrastructure.output.mongo.mapper;

import com.pragma.traceability_microservice.domain.model.StatusLog;
import com.pragma.traceability_microservice.domain.model.Traceability;
import com.pragma.traceability_microservice.infrastructure.output.mongo.document.StatusLogDocument;
import com.pragma.traceability_microservice.infrastructure.output.mongo.document.TraceabilityDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITraceabilityDocumentMapper {
    @Mapping(source = "statusLogs", target = "statusLogs", qualifiedByName = "statusLogListToStatusLogDocument")
    TraceabilityDocument traceabilityToDocument(Traceability traceability);

    StatusLogDocument statusLogToStatusLogDocument(StatusLog statusLog);

    @Named("statusLogListToStatusLogDocument")
    default List<StatusLogDocument> statusLogListToStatusLogDocument(List<StatusLog> statusLogs) {
        return statusLogs.stream()
                .map(this::statusLogToStatusLogDocument)
                .toList();
    }

    @Mapping(source = "statusLogs", target = "statusLogs", qualifiedByName = "statusLogDocumentToStatusLogList")
    Traceability traceabilityDocumentToTraceability(TraceabilityDocument traceabilityDocument);

    StatusLog statusLogDocumentToStatusLog(StatusLogDocument statusLogDocument);

    @Named("statusLogDocumentToStatusLogList")
    default List<StatusLog> statusLogDocumentToStatusLogList(List<StatusLogDocument> statusLogDocuments) {
        return statusLogDocuments.stream()
                .map(this::statusLogDocumentToStatusLog)
                .toList();
    }

    List<Traceability> traceabilityDocumentToTraceabilityList(List<TraceabilityDocument> traceabilityDocumentList);
}
