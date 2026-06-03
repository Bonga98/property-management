# SOLID Principles in Dywili Property Management API

This document explains how the project applies each SOLID principle with real examples from the codebase.

---

## S — Single Responsibility Principle
> *Every class should have one job and one reason to change*

Each class in this project has a clearly defined single purpose:

| Class | Single Responsibility |
|---|---|
| `UserController` | Handle HTTP requests for users only |
| `PropertyController` | Handle HTTP requests for properties only |
| `UserServiceImpl` | Business logic for users only |
| `PropertyServiceImpl` | Business logic for properties only |
| `UserConvertor` | Convert between UserDTO and UserEntity only |
| `PropertyConvertor` | Convert between PropertyDTO and PropertyEntity only |
| `JwtUtil` | Generate and validate JWT tokens only |
| `JwtFilter` | Intercept requests and check tokens only |
| `AppConfig` | Define Spring beans only |
| `SecurityConfig` | Configure security rules only |

**Example — `UserConvertor` only converts objects. It does not validate, save, or apply business logic:**
```java
public UserEntity convertDTOtoEntity(UserDTO userDTO) { ... }
public UserDTO convertEntitytoDTO(UserEntity userEntity) { ... }
```

---

## O — Open/Closed Principle
> *Classes should be open for extension but closed for modification*

Service interfaces are closed for modification. New behaviour is added by creating new implementations, not editing existing ones.

```java
// Closed for modification
public interface UserService {
    UserDTO register(UserDTO userDTO);
    UserDTO login(String email, String password);
}

// Open for extension — a new login strategy can be added
// by creating OAuthUserServiceImpl without touching UserService
@Service
public class UserServiceImpl implements UserService {
    ...
}
```

If a new login strategy (e.g. Google OAuth) was needed, a new class `OAuthUserServiceImpl` would be created — nothing in the existing code would change.

---

## L — Liskov Substitution Principle
> *You should be able to replace a class with its subtype without breaking anything*

Controllers depend on interfaces, not concrete implementations. Any class that implements the interface can be swapped in without the controller noticing.

```java
// Controller depends on the interface — not the concrete class
@Autowired
private UserService userService; // ✅ interface

// NOT this:
@Autowired
private UserServiceImpl userService; // ❌ concrete class
```

`UserServiceImpl` can be replaced with any other class that implements `UserService` and `UserController` would not break or change.

---

## I — Interface Segregation Principle
> *Don't force a class to implement methods it doesn't need*

Interfaces are kept small and focused on their own domain. User and property concerns are completely separated.

```java
// UserService only has user-related methods
public interface UserService {
    UserDTO register(UserDTO userDTO);
    UserDTO login(String email, String password);
}

// PropertyService only has property-related methods
public interface PropertyService {
    PropertyDTO saveProperty(PropertyDTO propertyDTO, String email);
    List<PropertyDTO> getAllproperties(String email);
    PropertyDTO updateProperty(PropertyDTO propertyDTO, Long propertyID);
    PropertyDTO updatePropertyDescription(PropertyDTO propertyDTO, Long propertyID);
    void deleteProperty(Long propertyID);
}
```

`PropertyServiceImpl` is never forced to implement user methods and `UserServiceImpl` is never forced to implement property methods.

---

## D — Dependency Inversion Principle
> *High level classes should not depend on low level classes — both should depend on abstractions*

This is applied throughout the entire project using Spring's `@Autowired`. Classes declare what they need and Spring injects it — nothing is manually instantiated with `new`.

```java
// PropertyController (high level) depends on abstractions
@Autowired
private PropertyService propertyService; // ← interface, not implementation

// UserServiceImpl (high level) depends on abstractions
@Autowired
private UserRepository userRepository;      // ← interface
@Autowired
private BCryptPasswordEncoder passwordEncoder; // ← injected, not new'd up
@Autowired
private JwtUtil jwtUtil;                    // ← injected, not new'd up
```

Spring manages the lifecycle and injection of all dependencies — the classes themselves never create their own dependencies.

---

## Summary

| Principle | How it's applied |
|---|---|
| **S** — Single Responsibility | Each class has one job: controllers handle HTTP, services handle logic, convertors handle mapping |
| **O** — Open/Closed | New behaviour is added via new implementations, not by editing existing interfaces |
| **L** — Liskov Substitution | Controllers depend on interfaces so implementations can be swapped freely |
| **I** — Interface Segregation | `UserService` and `PropertyService` are separate — no class is forced to implement unrelated methods |
| **D** — Dependency Inversion | All dependencies are injected by Spring via `@Autowired` — no `new` keyword for dependencies |
