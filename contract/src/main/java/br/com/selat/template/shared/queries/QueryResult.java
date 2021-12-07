package br.com.selat.template.shared.queries;

import java.util.List;

public record QueryResult<T> (List<T> result, Long totalElements){}
