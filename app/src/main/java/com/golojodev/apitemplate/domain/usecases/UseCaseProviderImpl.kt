package com.golojodev.apitemplate.domain.usecases

class UseCaseProviderImpl(
    override val onSaveTheme: SaveThemeUseCase,
    override val onGetTheme: GetThemeUseCase,
    override val onGetModels: GetModelsUseCase,
    override val onFetchModels: FetchModelsUseCase,
    override val onUpdateModel: UpdateModelUseCase,
    override val onGetFavorites: GetFavoritesUseCase
) : UseCaseProvider