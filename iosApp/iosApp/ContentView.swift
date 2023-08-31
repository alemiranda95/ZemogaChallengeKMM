import SwiftUI
import shared
import KMMViewModelSwiftUI

struct ContentView: View {

    @State var showSplash = true
    
    var body: some View {
        if (showSplash) {
            ComposeView()
                .ignoresSafeArea(.all)
                .onAppear {
                    DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
                        showSplash = false
                    }
                }
        } else {
            if #available(iOS 16.0, *) {
                MainView(databaseDriverFactory: DatabaseDriverFactory())
                    .transition(.opacity)
                    .animation(.easeIn)
            } else {
                // Fallback on earlier versions
            }
        }
    }
}
