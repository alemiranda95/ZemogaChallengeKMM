//
//  Image.swift
//  iosApp
//
//  Created by Alejandro Miranda on 2023-07-05.
//  Copyright © 2023 orgName. All rights reserved.
//


import Foundation
import shared
import SwiftUI

extension Image {
    init(resource: KeyPath<SharedResources.images, ImageResource>) {
        self.init(uiImage: SharedResources.images()[keyPath: resource].toUIImage()!)
    }
}
