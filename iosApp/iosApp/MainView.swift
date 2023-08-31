//
//  MainView.swift
//  iosApp
//
//  Created by Alejandro Miranda on 2023-08-24.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import KMMViewModelSwiftUI

@available(iOS 16.0, *)
struct MainView: View {
    
    @ObservedViewModel var viewModel: PostViewModel

    init() {
        viewModel = PostViewModel(databaseDriverFactory: DatabaseDriverFactory())
        viewModel.getPostsFromDB()
    }
    
    var body: some View {
        let uiState = viewModel.uiState
        
        NavigationStack {
            ZStack() {
                List(uiState.posts, id: \.self) { post in
                    VStack(alignment: .leading) {
                        Text(post.title)
                            .font(.headline)
                            .lineLimit(1)
                        Text(post.body)
                            .font(.body)
                            .lineLimit(3)
                    }
                }.refreshable {
                    viewModel.getPostsFromApi()
                }
                if (uiState.posts.isEmpty) {
                    VStack {
                        Text(
                            Strings().get(id: SharedResources.strings().pull_down_to_load_label, args: [])
                        )
                        Image(resource: \.arrow_downward)
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(maxWidth: 20, maxHeight: 20)
                    }
                }
            }
            .toolbar {
                if (!uiState.posts.isEmpty) {
                    Button("Clear All") {
                        viewModel.deletePosts()
                    }
                }
            }
        }
    }
    
}
