//
//  ComposeView.swift
//  iosApp
//
//  Created by Alejandro Miranda on 2023-08-29.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

struct ComposeView: UIViewControllerRepresentable {
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
    }
    
    func makeUIViewController(context: Context) -> some UIViewController {
        Splash_iosKt.MainViewController()
    }
}
