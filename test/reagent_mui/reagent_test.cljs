(ns reagent-mui.reagent-test
  (:require-macros [reagent-mui.util :refer [e react-component]])
  (:require [react :as react]
            [cljs.test :refer-macros [deftest testing is use-fixtures]]
            [dommy.core :as dommy :refer-macros [sel1]]
            [reagent.core :as r]
            [reagent-mui.styles :refer [styled]]
            [reagent-mui.test-util :refer [unmount-fixture render]]))

(use-fixtures :each unmount-fixture)

(defn p [text]
  [:p text])

(deftest react-component-test
  (let [ref (react/createRef)
        react-wrapper (fn [props]
                        (e "div" #js {:id "wrapper"}
                           (.render props #js {:displayText "foobar"
                                               :ref         ref})))
        component (react-component [props]
                    [:div#wrapped-component (dissoc props :display-text) (:display-text props)])]
    (testing "react-component"
      (render [:> react-wrapper {:render component}])
      (is (= "foobar" (dommy/text (sel1 "#wrapped-component"))))
      (is (= "wrapped-component" (.-id (.-current ref)))))))
