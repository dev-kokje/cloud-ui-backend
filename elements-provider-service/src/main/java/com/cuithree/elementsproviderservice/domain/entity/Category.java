package com.cuithree.elementsproviderservice.domain.entity;

import java.util.List;

public record Category(
   String name,
   String alternateName,
   List<Element> elements
) {}
