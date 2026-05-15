# Architecture

## Pattern: MVVM

### Layers

UI Layer
- MainActivity
- RecyclerView Adapter

ViewModel Layer
- CatViewModel

Repository Layer
- CatRepository

Data Layer
- API Service (Retrofit)

## Flow

UI → ViewModel → Repository → API → Repository → ViewModel → UI