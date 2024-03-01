# Project Motif.md

## Project Initiation

This project originated from a request by a firefighter at Gangnam Fire Station in Seoul. The goal was to support the installation of fire prevention facilities within buildings.

## Project Objectives

### 1. Simplified View for Property Owners

Create a user-friendly interface for property owners to:

- Reference their property via the address.
- Understand the required fire safety installations based on the Fire Safety Installation Laws #2 and #4.

### 2. Simplified View for the Fire Prevention Team

Provide a concise summary for the fire prevention team in Korea. They should be able to easily review the necessary facilities for each floor of buildings undergoing approval. This information can be input manually, utilizing various fields to describe each building and floor.

### 3. Formularization of Laws

Demonstrate how laws can be formalized and converted into a sequential boolean equation. The approach includes:

- Laws composed of clauses, where each clause involves chained sequences of AND and THEN relations.
- Different laws linked through OR relations (with the possibility of adding THEN relations in the future).

## Technology Stack

The project leverages the following technologies:

1. **Spring Boot** (version 3.2)
2. **Thymeleaf** (subject to change)
3. **JPA**
4. **Redis** (for caching)
5. **Microsoft Z3** (for verifying the formalized laws as an SMT equation, not yet implemented)

## Object Diagram

To be updated.
